package it.polimi.dice.dicer;

import java.io.IOException;
import java.util.Collections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.impl.resource.EMFTVMResourceFactoryImpl;
import org.eclipse.m2m.atl.emftvm.util.DefaultModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;
import org.eclipse.ocl.internal.helper.PluginFinder;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.unizar.disco.dice.DDSM.DDSMPackage;

public class ATLTransformationLauncher {

    private static final Logger logger = LoggerFactory.getLogger(ATLTransformationLauncher.class);

    // The input and output metamodel nsURIs are resolved using lazy registration of metamodels, see below.
    private String inputMetamodelNsURI;
    private String diceProfilePath;
    private String outputMetamodelNsURI;
    
    //Main transformation launch method
    public void launch(boolean uml, String inModelPath, String inMetaModelName,
            String outModelPath, String outMetaModelName, String transformationDir, String transformationModule){
        
        logger.info("Launching the model-to-model transformation.");

        /* 
         * Creates the execution environment where the transformation is going to be executed,
         * you could use an execution pool if you want to run multiple transformations in parallel,
         * but for the purpose of the example let's keep it simple.
         */
        ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
        ResourceSet rs = new ResourceSetImpl();


        /*
         * Load meta-models in the resource set we just created, the idea here is to make the meta-models
         * available in the context of the execution environment, the ResourceSet is later passed to the
         * ModuleResolver that is the actual class that will run the transformation.
         * Notice that we use the nsUris to locate the metamodels in the package registry, we initialize them 
         * from Ecore files that we registered lazily as shown below in e.g. registerInputMetamodel(...) 
         */
        Metamodel inMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
        inMetamodel.setResource(rs.getResource(URI.createURI(inputMetamodelNsURI), true));
        env.registerMetaModel(inMetaModelName, inMetamodel);

        Metamodel outMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
        outMetamodel.setResource(rs.getResource(URI.createURI(outputMetamodelNsURI), true));
        env.registerMetaModel(outMetaModelName, outMetamodel);
        
        /*
         * Create and register resource factories to read/parse .xmi and .emftvm files,
         * we need an .xmi parser because our in/output models are .xmi and our transformations are
         * compiled using the ATL-EMFTV compiler that generates .emftvm files
         */
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emftvm", new EMFTVMResourceFactoryImpl());

        if(uml){
            UMLResourcesUtil.init(rs);
            UMLPlugin.getEPackageNsURIToProfileLocationMap().put(DDSMPackage.eINSTANCE.getNsURI(), URI.createURI("pathmap://DICE_PROFILES/DICE.profile.uml#_aYmS0Dx2EeaOH59TuV453g"));
            rs.getURIConverter().getURIMap().put(URI.createURI("pathmap://DICE_PROFILES/DICE.profile.uml"), URI.createURI(diceProfilePath));
        }

        // Load models
        Model inModel = EmftvmFactory.eINSTANCE.createModel();
        inModel.setResource(rs.getResource(URI.createFileURI(new java.io.File(inModelPath).getAbsolutePath()), true));
        env.registerInputModel("uml", inModel);
    
        Model outModel = EmftvmFactory.eINSTANCE.createModel();
        outModel.setResource(rs.createResource(URI.createFileURI(new java.io.File(outModelPath).getAbsolutePath())));
        env.registerOutputModel("tosca", outModel);
        
        /*
         *  Load and run the transformation module
         *  Point at the directory your transformations are stored, the ModuleResolver will 
         *  look for the .emftvm file corresponding to the module you want to load and run
         */
        ModuleResolver mr = new DefaultModuleResolver(transformationDir, rs);
        TimingData td = new TimingData();
        env.loadModule(mr, transformationModule);
        td.finishLoading();
        env.run(td);
        td.finish();
            
        // Save models
        try {
            outModel.getResource().save(Collections.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getDiceProfilePath() {
        return diceProfilePath;
    }

    public void setDiceProfilePath(String diceProfilePath) {
        this.diceProfilePath = diceProfilePath;
    }

    /*
     * I seriously hate relying on the eclipse facilities, and if you're not building an eclipse plugin
     * you can't rely on eclipse's registry (let's say you're building a stand-alone tool that needs to run ATL
     * transformation, you need to 'manually' register your metamodels) 
     * This method does two things, it initializes an Ecore parser and then programmatically looks for
     * the package definition on it, obtains the NsUri and registers it.
     */
    private String lazyMetamodelRegistration(String metamodelPath){
        
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
    
        ResourceSet rs = new ResourceSetImpl();
        // Enables extended meta-data, weird we have to do this but well...
        final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
        rs.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
    
        Resource r = rs.getResource(URI.createFileURI(new java.io.File(metamodelPath).getAbsolutePath()), true);
        EObject eObject = r.getContents().get(0);
        // A meta-model might have multiple packages we assume the main package is the first one listed
        if (eObject instanceof EPackage) {
            EPackage p = (EPackage)eObject;
            EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
            return p.getNsURI();
        }
        return null;
    }
    
    /*
     * As shown above we need the inputMetamodelNsURI and the outputMetamodelNsURI to create the context of
     * the transformation, so we simply use the return value of lazyMetamodelRegistration to store them.
     * -- Notice that the lazyMetamodelRegistration(..) implementation may return null in case it doesn't 
     * find a package in the given metamodel, so watch out for malformed metamodels.
     * 
     */
    public void registerInputMetamodel(String inputMetamodelPath){  
        inputMetamodelNsURI = lazyMetamodelRegistration(inputMetamodelPath);
    }

    public String getInputMetamodelNsURI() {
        return inputMetamodelNsURI;
    }

    public void setInputMetamodelNsURI(String inputMetamodelNsURI) {
        this.inputMetamodelNsURI = inputMetamodelNsURI;
    }

    public String getOutputMetamodelNsURI() {
        return outputMetamodelNsURI;
    }

    public void setOutputMetamodelNsURI(String outputMetamodelNsURI) {
        this.outputMetamodelNsURI = outputMetamodelNsURI;
    }

    public void registerOutputMetamodel(String outputMetamodelPath){
        outputMetamodelNsURI = lazyMetamodelRegistration(outputMetamodelPath);
    }
    
    public void registerMetamodel(String metamodelPath){
       lazyMetamodelRegistration(metamodelPath);
    }

}