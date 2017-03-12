/*
 * generated by Xtext 2.10.0
 */
package it.polimi.dice.dicer.serializer;

import com.google.inject.Inject;
import it.polimi.dice.dicer.services.ToscaDslGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class ToscaDslSyntacticSequencer extends AbstractSyntacticSequencer {

	protected ToscaDslGrammarAccess grammarAccess;
	protected AbstractElementAlias match_NodeTemplate___ConfigurationKeyword_12_0_RightCurlyBracketKeyword_12_2__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (ToscaDslGrammarAccess) access;
		match_NodeTemplate___ConfigurationKeyword_12_0_RightCurlyBracketKeyword_12_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getNodeTemplateAccess().getConfigurationKeyword_12_0()), new TokenAlias(false, false, grammarAccess.getNodeTemplateAccess().getRightCurlyBracketKeyword_12_2()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_NodeTemplate___ConfigurationKeyword_12_0_RightCurlyBracketKeyword_12_2__q.equals(syntax))
				emit_NodeTemplate___ConfigurationKeyword_12_0_RightCurlyBracketKeyword_12_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     (
	  *         '
	  *         "configuration" : {' 
	  *         '}'
	  *     )?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         description=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "capabilities" :' 
	 *         '{' 
	 *         capabilities+=Capability
	 *     )
	 *     (
	 *         description=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "requirements" :' 
	 *         '{' 
	 *         requirements+=Requirement
	 *     )
	 *     (
	 *         description=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         '}' 
	 *         (rule end)
	 *     )
	 *     (
	 *         instances=Instances 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "capabilities" :' 
	 *         '{' 
	 *         capabilities+=Capability
	 *     )
	 *     (
	 *         instances=Instances 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "requirements" :' 
	 *         '{' 
	 *         requirements+=Requirement
	 *     )
	 *     (
	 *         instances=Instances 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         '}' 
	 *         (rule end)
	 *     )
	 *     (
	 *         interfaces+=Interface 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "capabilities" :' 
	 *         '{' 
	 *         capabilities+=Capability
	 *     )
	 *     (
	 *         interfaces+=Interface 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "requirements" :' 
	 *         '{' 
	 *         requirements+=Requirement
	 *     )
	 *     (
	 *         interfaces+=Interface 
	 *         '}' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         '}' 
	 *         (rule end)
	 *     )
	 *     (
	 *         relationships+=Relationship 
	 *         ']' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "capabilities" :' 
	 *         '{' 
	 *         capabilities+=Capability
	 *     )
	 *     (
	 *         relationships+=Relationship 
	 *         ']' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "requirements" :' 
	 *         '{' 
	 *         requirements+=Requirement
	 *     )
	 *     (
	 *         relationships+=Relationship 
	 *         ']' 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         '}' 
	 *         (rule end)
	 *     )
	 *     (
	 *         type=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "capabilities" :' 
	 *         '{' 
	 *         capabilities+=Capability
	 *     )
	 *     (
	 *         type=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         ',
	 *         "requirements" :' 
	 *         '{' 
	 *         requirements+=Requirement
	 *     )
	 *     (
	 *         type=STRING 
	 *         ',
	 *         "properties" :' 
	 *         '{' 
	 *         (ambiguity) 
	 *         '}' 
	 *         '}' 
	 *         (rule end)
	 *     )
	 */
	protected void emit_NodeTemplate___ConfigurationKeyword_12_0_RightCurlyBracketKeyword_12_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}