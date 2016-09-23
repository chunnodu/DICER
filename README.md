# DICER

DICER is a tool developed in the context of the DICE H2020 European Project with the goal of supporting the deployment and management of Big Data applications. 
Main goal of DICER is to exploit deployment models specified in accordance with the DICE Deployment Specific metamodel, in order to speed up the deployment process.

# Requirement

DICER leverages the DICE Deployment Specific Metamodel (DDSM), which can be directly used to create DICER-processable models. The metamodel is included in the DICER project. Right now DICER does not provide its own GUI and operates as a simple java tool, runnable from the command line. To create the input models, you can use the Eclipse Reflective Ecore Model Diagram Editor, available at the following url:

http://dynamicgmf.sourceforge.net

You can download the 0.2.1 version (direct binary download url ()). Unzip the downloaded file and copy and paste extracted .jar into the "plugins" folder of your Eclipse installation.

The plugin requires the following additional Eclipse plugins:

3. [Ecore Tools](http://www.eclipse.org/ecoretools/): from the download tab, choose the 3.1.x Nightly update site. You can install just the Ecore Diagram Editor and the Ecore Diargam Editor SDK items. 

1. [GMF](http://www.eclipse.org/modeling/gmp/): use this [update site](http://download.eclipse.org/modeling/gmp/gmf-runtime/updates/releases/) to install just the necessary GMF Runtime plugin.

2. [EMF](https://eclipse.org/modeling/emf/): use this [update site] (http://download.eclipse.org/modeling/emf/updates/releases/). You can install just one of the available EMF SDK items (suggested version 2.4.2).

At this step the required environment for creating EMF models from an Ecore metamodel is ready.
Once the DICER metamodels are imported into Eclipse you can use the Reflective Ecore Model Diagram Editor plugin to start creating DICER models.

If you download the dicer-full.zip release, you can create a new general project and import the dicer-full.zip released archive or directly, Otherwise you can just checkout and import the maven project contained in this repository. At this point all you have to do is to use the now available "Reflective Ecore Diagram Editor" option from the "New" wizard. 

# Installation

The DICER is at its early ages and has not been released yet. We plan to release it in the next few weeks. Right now if you want to use the DICER you can checkout this repository and compile it with maven.
Make sure to put the compiled artifact in the same folder in which the metamodels/ and transformations/ folders are located ( the root folder in the case you checkout the source code). You may using the following commands:

    git clone https://github.com/DICERs/DICER.git
    cd DICER
    mvn clean package
    cd dicer-core
    cp target/dicer-core-0.1.0.jar .

# Usage

In order to run DICER, assuming that you already created a DDSM model using the provided metamodel and with the help of the Ecore Reflective Diagram Editor, you just need to run compiler .jar artifact giving as input the path to the input DDSM model and the path to the output TOSCA models. In order to run DICER against one o the available models you can execute following command:

    java -jar dicer-core-0.1.0.jar -inModel models/storm_cluster.xmi -outModel models/storm_cluster_tosca

Make sure that the -outModel argument is a path to a file with no extention. DICER will create also an xmi and a json version of the generated deployment blueprint.
