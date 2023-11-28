package asmeta.avallaxt.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import asmeta.avallaxt.services.AvallaGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAvallaParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_GOOD_CHARS_NO_COLON", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_GOOD_CHAR_NO_COLON", "'scenario'", "'load'", "'invariant'", "':'", "';'", "'check'", "'set'", "':='", "'step'", "'until'", "'exec'", "'begin'", "'end'", "'execblock'"
    };
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=7;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int T__10=10;
    public static final int RULE_GOOD_CHARS_NO_COLON=4;
    public static final int RULE_WS=8;
    public static final int RULE_GOOD_CHAR_NO_COLON=9;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=6;
    public static final int T__23=23;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalAvallaParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalAvallaParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalAvallaParser.tokenNames; }
    public String getGrammarFileName() { return "InternalAvalla.g"; }



     	private AvallaGrammarAccess grammarAccess;

        public InternalAvallaParser(TokenStream input, AvallaGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Scenario";
       	}

       	@Override
       	protected AvallaGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleScenario"
    // InternalAvalla.g:64:1: entryRuleScenario returns [EObject current=null] : iv_ruleScenario= ruleScenario EOF ;
    public final EObject entryRuleScenario() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScenario = null;


        try {
            // InternalAvalla.g:64:49: (iv_ruleScenario= ruleScenario EOF )
            // InternalAvalla.g:65:2: iv_ruleScenario= ruleScenario EOF
            {
             newCompositeNode(grammarAccess.getScenarioRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleScenario=ruleScenario();

            state._fsp--;

             current =iv_ruleScenario; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScenario"


    // $ANTLR start "ruleScenario"
    // InternalAvalla.g:71:1: ruleScenario returns [EObject current=null] : (otherlv_0= 'scenario' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= 'load' ( (lv_spec_3_0= rulePath ) ) ( (lv_invariants_4_0= ruleInvariant ) )* ( (lv_elements_5_0= ruleElement ) )* ) ;
    public final EObject ruleScenario() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_spec_3_0 = null;

        EObject lv_invariants_4_0 = null;

        EObject lv_elements_5_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:77:2: ( (otherlv_0= 'scenario' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= 'load' ( (lv_spec_3_0= rulePath ) ) ( (lv_invariants_4_0= ruleInvariant ) )* ( (lv_elements_5_0= ruleElement ) )* ) )
            // InternalAvalla.g:78:2: (otherlv_0= 'scenario' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= 'load' ( (lv_spec_3_0= rulePath ) ) ( (lv_invariants_4_0= ruleInvariant ) )* ( (lv_elements_5_0= ruleElement ) )* )
            {
            // InternalAvalla.g:78:2: (otherlv_0= 'scenario' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= 'load' ( (lv_spec_3_0= rulePath ) ) ( (lv_invariants_4_0= ruleInvariant ) )* ( (lv_elements_5_0= ruleElement ) )* )
            // InternalAvalla.g:79:3: otherlv_0= 'scenario' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= 'load' ( (lv_spec_3_0= rulePath ) ) ( (lv_invariants_4_0= ruleInvariant ) )* ( (lv_elements_5_0= ruleElement ) )*
            {
            otherlv_0=(Token)match(input,10,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getScenarioAccess().getScenarioKeyword_0());
            		
            // InternalAvalla.g:83:3: ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:84:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:84:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:85:5: lv_name_1_0= RULE_GOOD_CHARS_NO_COLON
            {
            lv_name_1_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getScenarioAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getScenarioRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"asmeta.avallaxt.Avalla.GOOD_CHARS_NO_COLON");
            				

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getScenarioAccess().getLoadKeyword_2());
            		
            // InternalAvalla.g:105:3: ( (lv_spec_3_0= rulePath ) )
            // InternalAvalla.g:106:4: (lv_spec_3_0= rulePath )
            {
            // InternalAvalla.g:106:4: (lv_spec_3_0= rulePath )
            // InternalAvalla.g:107:5: lv_spec_3_0= rulePath
            {

            					newCompositeNode(grammarAccess.getScenarioAccess().getSpecPathParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_6);
            lv_spec_3_0=rulePath();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getScenarioRule());
            					}
            					set(
            						current,
            						"spec",
            						lv_spec_3_0,
            						"asmeta.avallaxt.Avalla.Path");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAvalla.g:124:3: ( (lv_invariants_4_0= ruleInvariant ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAvalla.g:125:4: (lv_invariants_4_0= ruleInvariant )
            	    {
            	    // InternalAvalla.g:125:4: (lv_invariants_4_0= ruleInvariant )
            	    // InternalAvalla.g:126:5: lv_invariants_4_0= ruleInvariant
            	    {

            	    					newCompositeNode(grammarAccess.getScenarioAccess().getInvariantsInvariantParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_6);
            	    lv_invariants_4_0=ruleInvariant();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getScenarioRule());
            	    					}
            	    					add(
            	    						current,
            	    						"invariants",
            	    						lv_invariants_4_0,
            	    						"asmeta.avallaxt.Avalla.Invariant");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalAvalla.g:143:3: ( (lv_elements_5_0= ruleElement ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=15 && LA2_0<=16)||LA2_0==18||(LA2_0>=20 && LA2_0<=21)||LA2_0==23) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalAvalla.g:144:4: (lv_elements_5_0= ruleElement )
            	    {
            	    // InternalAvalla.g:144:4: (lv_elements_5_0= ruleElement )
            	    // InternalAvalla.g:145:5: lv_elements_5_0= ruleElement
            	    {

            	    					newCompositeNode(grammarAccess.getScenarioAccess().getElementsElementParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_elements_5_0=ruleElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getScenarioRule());
            	    					}
            	    					add(
            	    						current,
            	    						"elements",
            	    						lv_elements_5_0,
            	    						"asmeta.avallaxt.Avalla.Element");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScenario"


    // $ANTLR start "entryRuleInvariant"
    // InternalAvalla.g:166:1: entryRuleInvariant returns [EObject current=null] : iv_ruleInvariant= ruleInvariant EOF ;
    public final EObject entryRuleInvariant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInvariant = null;


        try {
            // InternalAvalla.g:166:50: (iv_ruleInvariant= ruleInvariant EOF )
            // InternalAvalla.g:167:2: iv_ruleInvariant= ruleInvariant EOF
            {
             newCompositeNode(grammarAccess.getInvariantRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInvariant=ruleInvariant();

            state._fsp--;

             current =iv_ruleInvariant; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInvariant"


    // $ANTLR start "ruleInvariant"
    // InternalAvalla.g:173:1: ruleInvariant returns [EObject current=null] : (otherlv_0= 'invariant' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' ( (lv_expression_3_0= rulesentence ) ) otherlv_4= ';' ) ;
    public final EObject ruleInvariant() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:179:2: ( (otherlv_0= 'invariant' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' ( (lv_expression_3_0= rulesentence ) ) otherlv_4= ';' ) )
            // InternalAvalla.g:180:2: (otherlv_0= 'invariant' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' ( (lv_expression_3_0= rulesentence ) ) otherlv_4= ';' )
            {
            // InternalAvalla.g:180:2: (otherlv_0= 'invariant' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' ( (lv_expression_3_0= rulesentence ) ) otherlv_4= ';' )
            // InternalAvalla.g:181:3: otherlv_0= 'invariant' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' ( (lv_expression_3_0= rulesentence ) ) otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getInvariantAccess().getInvariantKeyword_0());
            		
            // InternalAvalla.g:185:3: ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:186:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:186:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:187:5: lv_name_1_0= RULE_GOOD_CHARS_NO_COLON
            {
            lv_name_1_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_8); 

            					newLeafNode(lv_name_1_0, grammarAccess.getInvariantAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInvariantRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"asmeta.avallaxt.Avalla.GOOD_CHARS_NO_COLON");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getInvariantAccess().getColonKeyword_2());
            		
            // InternalAvalla.g:207:3: ( (lv_expression_3_0= rulesentence ) )
            // InternalAvalla.g:208:4: (lv_expression_3_0= rulesentence )
            {
            // InternalAvalla.g:208:4: (lv_expression_3_0= rulesentence )
            // InternalAvalla.g:209:5: lv_expression_3_0= rulesentence
            {

            					newCompositeNode(grammarAccess.getInvariantAccess().getExpressionSentenceParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_10);
            lv_expression_3_0=rulesentence();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInvariantRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_3_0,
            						"asmeta.avallaxt.Avalla.sentence");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getInvariantAccess().getSemicolonKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInvariant"


    // $ANTLR start "entryRuleElement"
    // InternalAvalla.g:234:1: entryRuleElement returns [EObject current=null] : iv_ruleElement= ruleElement EOF ;
    public final EObject entryRuleElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleElement = null;


        try {
            // InternalAvalla.g:234:48: (iv_ruleElement= ruleElement EOF )
            // InternalAvalla.g:235:2: iv_ruleElement= ruleElement EOF
            {
             newCompositeNode(grammarAccess.getElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleElement=ruleElement();

            state._fsp--;

             current =iv_ruleElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleElement"


    // $ANTLR start "ruleElement"
    // InternalAvalla.g:241:1: ruleElement returns [EObject current=null] : (this_Command_0= ruleCommand | this_Block_1= ruleBlock ) ;
    public final EObject ruleElement() throws RecognitionException {
        EObject current = null;

        EObject this_Command_0 = null;

        EObject this_Block_1 = null;



        	enterRule();

        try {
            // InternalAvalla.g:247:2: ( (this_Command_0= ruleCommand | this_Block_1= ruleBlock ) )
            // InternalAvalla.g:248:2: (this_Command_0= ruleCommand | this_Block_1= ruleBlock )
            {
            // InternalAvalla.g:248:2: (this_Command_0= ruleCommand | this_Block_1= ruleBlock )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=15 && LA3_0<=16)||LA3_0==18||LA3_0==20||LA3_0==23) ) {
                alt3=1;
            }
            else if ( (LA3_0==21) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalAvalla.g:249:3: this_Command_0= ruleCommand
                    {

                    			newCompositeNode(grammarAccess.getElementAccess().getCommandParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Command_0=ruleCommand();

                    state._fsp--;


                    			current = this_Command_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAvalla.g:258:3: this_Block_1= ruleBlock
                    {

                    			newCompositeNode(grammarAccess.getElementAccess().getBlockParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Block_1=ruleBlock();

                    state._fsp--;


                    			current = this_Block_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleElement"


    // $ANTLR start "entryRuleCommand"
    // InternalAvalla.g:270:1: entryRuleCommand returns [EObject current=null] : iv_ruleCommand= ruleCommand EOF ;
    public final EObject entryRuleCommand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCommand = null;


        try {
            // InternalAvalla.g:270:48: (iv_ruleCommand= ruleCommand EOF )
            // InternalAvalla.g:271:2: iv_ruleCommand= ruleCommand EOF
            {
             newCompositeNode(grammarAccess.getCommandRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCommand=ruleCommand();

            state._fsp--;

             current =iv_ruleCommand; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCommand"


    // $ANTLR start "ruleCommand"
    // InternalAvalla.g:277:1: ruleCommand returns [EObject current=null] : (this_Check_0= ruleCheck | this_Set_1= ruleSet | ( () ruleStep ) | this_StepUntil_4= ruleStepUntil | this_Exec_5= ruleExec | this_ExecBlock_6= ruleExecBlock ) ;
    public final EObject ruleCommand() throws RecognitionException {
        EObject current = null;

        EObject this_Check_0 = null;

        EObject this_Set_1 = null;

        EObject this_StepUntil_4 = null;

        EObject this_Exec_5 = null;

        EObject this_ExecBlock_6 = null;



        	enterRule();

        try {
            // InternalAvalla.g:283:2: ( (this_Check_0= ruleCheck | this_Set_1= ruleSet | ( () ruleStep ) | this_StepUntil_4= ruleStepUntil | this_Exec_5= ruleExec | this_ExecBlock_6= ruleExecBlock ) )
            // InternalAvalla.g:284:2: (this_Check_0= ruleCheck | this_Set_1= ruleSet | ( () ruleStep ) | this_StepUntil_4= ruleStepUntil | this_Exec_5= ruleExec | this_ExecBlock_6= ruleExecBlock )
            {
            // InternalAvalla.g:284:2: (this_Check_0= ruleCheck | this_Set_1= ruleSet | ( () ruleStep ) | this_StepUntil_4= ruleStepUntil | this_Exec_5= ruleExec | this_ExecBlock_6= ruleExecBlock )
            int alt4=6;
            switch ( input.LA(1) ) {
            case 15:
                {
                alt4=1;
                }
                break;
            case 16:
                {
                alt4=2;
                }
                break;
            case 18:
                {
                int LA4_3 = input.LA(2);

                if ( (LA4_3==19) ) {
                    alt4=4;
                }
                else if ( (LA4_3==EOF||(LA4_3>=15 && LA4_3<=16)||LA4_3==18||(LA4_3>=20 && LA4_3<=23)) ) {
                    alt4=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 3, input);

                    throw nvae;
                }
                }
                break;
            case 20:
                {
                alt4=5;
                }
                break;
            case 23:
                {
                alt4=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalAvalla.g:285:3: this_Check_0= ruleCheck
                    {

                    			newCompositeNode(grammarAccess.getCommandAccess().getCheckParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Check_0=ruleCheck();

                    state._fsp--;


                    			current = this_Check_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAvalla.g:294:3: this_Set_1= ruleSet
                    {

                    			newCompositeNode(grammarAccess.getCommandAccess().getSetParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Set_1=ruleSet();

                    state._fsp--;


                    			current = this_Set_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAvalla.g:303:3: ( () ruleStep )
                    {
                    // InternalAvalla.g:303:3: ( () ruleStep )
                    // InternalAvalla.g:304:4: () ruleStep
                    {
                    // InternalAvalla.g:304:4: ()
                    // InternalAvalla.g:305:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getCommandAccess().getStepAction_2_0(),
                    						current);
                    				

                    }


                    				newCompositeNode(grammarAccess.getCommandAccess().getStepParserRuleCall_2_1());
                    			
                    pushFollow(FOLLOW_2);
                    ruleStep();

                    state._fsp--;


                    				afterParserOrEnumRuleCall();
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalAvalla.g:320:3: this_StepUntil_4= ruleStepUntil
                    {

                    			newCompositeNode(grammarAccess.getCommandAccess().getStepUntilParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_StepUntil_4=ruleStepUntil();

                    state._fsp--;


                    			current = this_StepUntil_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalAvalla.g:329:3: this_Exec_5= ruleExec
                    {

                    			newCompositeNode(grammarAccess.getCommandAccess().getExecParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_Exec_5=ruleExec();

                    state._fsp--;


                    			current = this_Exec_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalAvalla.g:338:3: this_ExecBlock_6= ruleExecBlock
                    {

                    			newCompositeNode(grammarAccess.getCommandAccess().getExecBlockParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExecBlock_6=ruleExecBlock();

                    state._fsp--;


                    			current = this_ExecBlock_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCommand"


    // $ANTLR start "entryRuleCheck"
    // InternalAvalla.g:350:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // InternalAvalla.g:350:46: (iv_ruleCheck= ruleCheck EOF )
            // InternalAvalla.g:351:2: iv_ruleCheck= ruleCheck EOF
            {
             newCompositeNode(grammarAccess.getCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCheck=ruleCheck();

            state._fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheck"


    // $ANTLR start "ruleCheck"
    // InternalAvalla.g:357:1: ruleCheck returns [EObject current=null] : (otherlv_0= 'check' ( (lv_expression_1_0= rulesentence ) ) otherlv_2= ';' ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:363:2: ( (otherlv_0= 'check' ( (lv_expression_1_0= rulesentence ) ) otherlv_2= ';' ) )
            // InternalAvalla.g:364:2: (otherlv_0= 'check' ( (lv_expression_1_0= rulesentence ) ) otherlv_2= ';' )
            {
            // InternalAvalla.g:364:2: (otherlv_0= 'check' ( (lv_expression_1_0= rulesentence ) ) otherlv_2= ';' )
            // InternalAvalla.g:365:3: otherlv_0= 'check' ( (lv_expression_1_0= rulesentence ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getCheckAccess().getCheckKeyword_0());
            		
            // InternalAvalla.g:369:3: ( (lv_expression_1_0= rulesentence ) )
            // InternalAvalla.g:370:4: (lv_expression_1_0= rulesentence )
            {
            // InternalAvalla.g:370:4: (lv_expression_1_0= rulesentence )
            // InternalAvalla.g:371:5: lv_expression_1_0= rulesentence
            {

            					newCompositeNode(grammarAccess.getCheckAccess().getExpressionSentenceParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_10);
            lv_expression_1_0=rulesentence();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCheckRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"asmeta.avallaxt.Avalla.sentence");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getCheckAccess().getSemicolonKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheck"


    // $ANTLR start "entryRuleSet"
    // InternalAvalla.g:396:1: entryRuleSet returns [EObject current=null] : iv_ruleSet= ruleSet EOF ;
    public final EObject entryRuleSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSet = null;


        try {
            // InternalAvalla.g:396:44: (iv_ruleSet= ruleSet EOF )
            // InternalAvalla.g:397:2: iv_ruleSet= ruleSet EOF
            {
             newCompositeNode(grammarAccess.getSetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSet=ruleSet();

            state._fsp--;

             current =iv_ruleSet; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSet"


    // $ANTLR start "ruleSet"
    // InternalAvalla.g:403:1: ruleSet returns [EObject current=null] : (otherlv_0= 'set' ( (lv_location_1_0= rulesentence ) ) otherlv_2= ':=' ( (lv_value_3_0= rulesentence ) ) otherlv_4= ';' ) ;
    public final EObject ruleSet() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_location_1_0 = null;

        AntlrDatatypeRuleToken lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:409:2: ( (otherlv_0= 'set' ( (lv_location_1_0= rulesentence ) ) otherlv_2= ':=' ( (lv_value_3_0= rulesentence ) ) otherlv_4= ';' ) )
            // InternalAvalla.g:410:2: (otherlv_0= 'set' ( (lv_location_1_0= rulesentence ) ) otherlv_2= ':=' ( (lv_value_3_0= rulesentence ) ) otherlv_4= ';' )
            {
            // InternalAvalla.g:410:2: (otherlv_0= 'set' ( (lv_location_1_0= rulesentence ) ) otherlv_2= ':=' ( (lv_value_3_0= rulesentence ) ) otherlv_4= ';' )
            // InternalAvalla.g:411:3: otherlv_0= 'set' ( (lv_location_1_0= rulesentence ) ) otherlv_2= ':=' ( (lv_value_3_0= rulesentence ) ) otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getSetAccess().getSetKeyword_0());
            		
            // InternalAvalla.g:415:3: ( (lv_location_1_0= rulesentence ) )
            // InternalAvalla.g:416:4: (lv_location_1_0= rulesentence )
            {
            // InternalAvalla.g:416:4: (lv_location_1_0= rulesentence )
            // InternalAvalla.g:417:5: lv_location_1_0= rulesentence
            {

            					newCompositeNode(grammarAccess.getSetAccess().getLocationSentenceParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_11);
            lv_location_1_0=rulesentence();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSetRule());
            					}
            					set(
            						current,
            						"location",
            						lv_location_1_0,
            						"asmeta.avallaxt.Avalla.sentence");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getSetAccess().getColonEqualsSignKeyword_2());
            		
            // InternalAvalla.g:438:3: ( (lv_value_3_0= rulesentence ) )
            // InternalAvalla.g:439:4: (lv_value_3_0= rulesentence )
            {
            // InternalAvalla.g:439:4: (lv_value_3_0= rulesentence )
            // InternalAvalla.g:440:5: lv_value_3_0= rulesentence
            {

            					newCompositeNode(grammarAccess.getSetAccess().getValueSentenceParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_10);
            lv_value_3_0=rulesentence();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSetRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_3_0,
            						"asmeta.avallaxt.Avalla.sentence");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getSetAccess().getSemicolonKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSet"


    // $ANTLR start "entryRuleStep"
    // InternalAvalla.g:465:1: entryRuleStep returns [String current=null] : iv_ruleStep= ruleStep EOF ;
    public final String entryRuleStep() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleStep = null;


        try {
            // InternalAvalla.g:465:44: (iv_ruleStep= ruleStep EOF )
            // InternalAvalla.g:466:2: iv_ruleStep= ruleStep EOF
            {
             newCompositeNode(grammarAccess.getStepRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStep=ruleStep();

            state._fsp--;

             current =iv_ruleStep.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStep"


    // $ANTLR start "ruleStep"
    // InternalAvalla.g:472:1: ruleStep returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'step' ;
    public final AntlrDatatypeRuleToken ruleStep() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAvalla.g:478:2: (kw= 'step' )
            // InternalAvalla.g:479:2: kw= 'step'
            {
            kw=(Token)match(input,18,FOLLOW_2); 

            		current.merge(kw);
            		newLeafNode(kw, grammarAccess.getStepAccess().getStepKeyword());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStep"


    // $ANTLR start "entryRuleStepUntil"
    // InternalAvalla.g:487:1: entryRuleStepUntil returns [EObject current=null] : iv_ruleStepUntil= ruleStepUntil EOF ;
    public final EObject entryRuleStepUntil() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStepUntil = null;


        try {
            // InternalAvalla.g:487:50: (iv_ruleStepUntil= ruleStepUntil EOF )
            // InternalAvalla.g:488:2: iv_ruleStepUntil= ruleStepUntil EOF
            {
             newCompositeNode(grammarAccess.getStepUntilRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStepUntil=ruleStepUntil();

            state._fsp--;

             current =iv_ruleStepUntil; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStepUntil"


    // $ANTLR start "ruleStepUntil"
    // InternalAvalla.g:494:1: ruleStepUntil returns [EObject current=null] : (otherlv_0= 'step' otherlv_1= 'until' ( (lv_expression_2_0= rulesentence ) ) otherlv_3= ';' ) ;
    public final EObject ruleStepUntil() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_expression_2_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:500:2: ( (otherlv_0= 'step' otherlv_1= 'until' ( (lv_expression_2_0= rulesentence ) ) otherlv_3= ';' ) )
            // InternalAvalla.g:501:2: (otherlv_0= 'step' otherlv_1= 'until' ( (lv_expression_2_0= rulesentence ) ) otherlv_3= ';' )
            {
            // InternalAvalla.g:501:2: (otherlv_0= 'step' otherlv_1= 'until' ( (lv_expression_2_0= rulesentence ) ) otherlv_3= ';' )
            // InternalAvalla.g:502:3: otherlv_0= 'step' otherlv_1= 'until' ( (lv_expression_2_0= rulesentence ) ) otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getStepUntilAccess().getStepKeyword_0());
            		
            otherlv_1=(Token)match(input,19,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getStepUntilAccess().getUntilKeyword_1());
            		
            // InternalAvalla.g:510:3: ( (lv_expression_2_0= rulesentence ) )
            // InternalAvalla.g:511:4: (lv_expression_2_0= rulesentence )
            {
            // InternalAvalla.g:511:4: (lv_expression_2_0= rulesentence )
            // InternalAvalla.g:512:5: lv_expression_2_0= rulesentence
            {

            					newCompositeNode(grammarAccess.getStepUntilAccess().getExpressionSentenceParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_10);
            lv_expression_2_0=rulesentence();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStepUntilRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_2_0,
            						"asmeta.avallaxt.Avalla.sentence");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getStepUntilAccess().getSemicolonKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStepUntil"


    // $ANTLR start "entryRuleExec"
    // InternalAvalla.g:537:1: entryRuleExec returns [EObject current=null] : iv_ruleExec= ruleExec EOF ;
    public final EObject entryRuleExec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExec = null;


        try {
            // InternalAvalla.g:537:45: (iv_ruleExec= ruleExec EOF )
            // InternalAvalla.g:538:2: iv_ruleExec= ruleExec EOF
            {
             newCompositeNode(grammarAccess.getExecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExec=ruleExec();

            state._fsp--;

             current =iv_ruleExec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExec"


    // $ANTLR start "ruleExec"
    // InternalAvalla.g:544:1: ruleExec returns [EObject current=null] : (otherlv_0= 'exec' ( (lv_rule_1_0= rulesentencePlusAssign ) ) otherlv_2= ';' ) ;
    public final EObject ruleExec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_rule_1_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:550:2: ( (otherlv_0= 'exec' ( (lv_rule_1_0= rulesentencePlusAssign ) ) otherlv_2= ';' ) )
            // InternalAvalla.g:551:2: (otherlv_0= 'exec' ( (lv_rule_1_0= rulesentencePlusAssign ) ) otherlv_2= ';' )
            {
            // InternalAvalla.g:551:2: (otherlv_0= 'exec' ( (lv_rule_1_0= rulesentencePlusAssign ) ) otherlv_2= ';' )
            // InternalAvalla.g:552:3: otherlv_0= 'exec' ( (lv_rule_1_0= rulesentencePlusAssign ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,20,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getExecAccess().getExecKeyword_0());
            		
            // InternalAvalla.g:556:3: ( (lv_rule_1_0= rulesentencePlusAssign ) )
            // InternalAvalla.g:557:4: (lv_rule_1_0= rulesentencePlusAssign )
            {
            // InternalAvalla.g:557:4: (lv_rule_1_0= rulesentencePlusAssign )
            // InternalAvalla.g:558:5: lv_rule_1_0= rulesentencePlusAssign
            {

            					newCompositeNode(grammarAccess.getExecAccess().getRuleSentencePlusAssignParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_10);
            lv_rule_1_0=rulesentencePlusAssign();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExecRule());
            					}
            					set(
            						current,
            						"rule",
            						lv_rule_1_0,
            						"asmeta.avallaxt.Avalla.sentencePlusAssign");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getExecAccess().getSemicolonKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExec"


    // $ANTLR start "entryRuleBlock"
    // InternalAvalla.g:583:1: entryRuleBlock returns [EObject current=null] : iv_ruleBlock= ruleBlock EOF ;
    public final EObject entryRuleBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBlock = null;


        try {
            // InternalAvalla.g:583:46: (iv_ruleBlock= ruleBlock EOF )
            // InternalAvalla.g:584:2: iv_ruleBlock= ruleBlock EOF
            {
             newCompositeNode(grammarAccess.getBlockRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBlock=ruleBlock();

            state._fsp--;

             current =iv_ruleBlock; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBlock"


    // $ANTLR start "ruleBlock"
    // InternalAvalla.g:590:1: ruleBlock returns [EObject current=null] : (otherlv_0= 'begin' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) ( (lv_elements_2_0= ruleElement ) )* otherlv_3= 'end' ) ;
    public final EObject ruleBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalAvalla.g:596:2: ( (otherlv_0= 'begin' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) ( (lv_elements_2_0= ruleElement ) )* otherlv_3= 'end' ) )
            // InternalAvalla.g:597:2: (otherlv_0= 'begin' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) ( (lv_elements_2_0= ruleElement ) )* otherlv_3= 'end' )
            {
            // InternalAvalla.g:597:2: (otherlv_0= 'begin' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) ( (lv_elements_2_0= ruleElement ) )* otherlv_3= 'end' )
            // InternalAvalla.g:598:3: otherlv_0= 'begin' ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) ) ( (lv_elements_2_0= ruleElement ) )* otherlv_3= 'end'
            {
            otherlv_0=(Token)match(input,21,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getBlockAccess().getBeginKeyword_0());
            		
            // InternalAvalla.g:602:3: ( (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:603:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:603:4: (lv_name_1_0= RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:604:5: lv_name_1_0= RULE_GOOD_CHARS_NO_COLON
            {
            lv_name_1_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_14); 

            					newLeafNode(lv_name_1_0, grammarAccess.getBlockAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBlockRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"asmeta.avallaxt.Avalla.GOOD_CHARS_NO_COLON");
            				

            }


            }

            // InternalAvalla.g:620:3: ( (lv_elements_2_0= ruleElement ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=15 && LA5_0<=16)||LA5_0==18||(LA5_0>=20 && LA5_0<=21)||LA5_0==23) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalAvalla.g:621:4: (lv_elements_2_0= ruleElement )
            	    {
            	    // InternalAvalla.g:621:4: (lv_elements_2_0= ruleElement )
            	    // InternalAvalla.g:622:5: lv_elements_2_0= ruleElement
            	    {

            	    					newCompositeNode(grammarAccess.getBlockAccess().getElementsElementParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_elements_2_0=ruleElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getBlockRule());
            	    					}
            	    					add(
            	    						current,
            	    						"elements",
            	    						lv_elements_2_0,
            	    						"asmeta.avallaxt.Avalla.Element");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_3=(Token)match(input,22,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getBlockAccess().getEndKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBlock"


    // $ANTLR start "entryRuleExecBlock"
    // InternalAvalla.g:647:1: entryRuleExecBlock returns [EObject current=null] : iv_ruleExecBlock= ruleExecBlock EOF ;
    public final EObject entryRuleExecBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExecBlock = null;


        try {
            // InternalAvalla.g:647:50: (iv_ruleExecBlock= ruleExecBlock EOF )
            // InternalAvalla.g:648:2: iv_ruleExecBlock= ruleExecBlock EOF
            {
             newCompositeNode(grammarAccess.getExecBlockRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExecBlock=ruleExecBlock();

            state._fsp--;

             current =iv_ruleExecBlock; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExecBlock"


    // $ANTLR start "ruleExecBlock"
    // InternalAvalla.g:654:1: ruleExecBlock returns [EObject current=null] : (otherlv_0= 'execblock' ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )? ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_4= ';' ) ;
    public final EObject ruleExecBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scenario_1_0=null;
        Token otherlv_2=null;
        Token lv_block_3_0=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalAvalla.g:660:2: ( (otherlv_0= 'execblock' ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )? ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_4= ';' ) )
            // InternalAvalla.g:661:2: (otherlv_0= 'execblock' ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )? ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_4= ';' )
            {
            // InternalAvalla.g:661:2: (otherlv_0= 'execblock' ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )? ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_4= ';' )
            // InternalAvalla.g:662:3: otherlv_0= 'execblock' ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )? ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,23,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getExecBlockAccess().getExecblockKeyword_0());
            		
            // InternalAvalla.g:666:3: ( ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_GOOD_CHARS_NO_COLON) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==13) ) {
                    alt6=1;
                }
            }
            switch (alt6) {
                case 1 :
                    // InternalAvalla.g:667:4: ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) ) otherlv_2= ':'
                    {
                    // InternalAvalla.g:667:4: ( (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON ) )
                    // InternalAvalla.g:668:5: (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON )
                    {
                    // InternalAvalla.g:668:5: (lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON )
                    // InternalAvalla.g:669:6: lv_scenario_1_0= RULE_GOOD_CHARS_NO_COLON
                    {
                    lv_scenario_1_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_8); 

                    						newLeafNode(lv_scenario_1_0, grammarAccess.getExecBlockAccess().getScenarioGOOD_CHARS_NO_COLONTerminalRuleCall_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getExecBlockRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"scenario",
                    							lv_scenario_1_0,
                    							"asmeta.avallaxt.Avalla.GOOD_CHARS_NO_COLON");
                    					

                    }


                    }

                    otherlv_2=(Token)match(input,13,FOLLOW_3); 

                    				newLeafNode(otherlv_2, grammarAccess.getExecBlockAccess().getColonKeyword_1_1());
                    			

                    }
                    break;

            }

            // InternalAvalla.g:690:3: ( (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:691:4: (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:691:4: (lv_block_3_0= RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:692:5: lv_block_3_0= RULE_GOOD_CHARS_NO_COLON
            {
            lv_block_3_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_10); 

            					newLeafNode(lv_block_3_0, grammarAccess.getExecBlockAccess().getBlockGOOD_CHARS_NO_COLONTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExecBlockRule());
            					}
            					setWithLastConsumed(
            						current,
            						"block",
            						lv_block_3_0,
            						"asmeta.avallaxt.Avalla.GOOD_CHARS_NO_COLON");
            				

            }


            }

            otherlv_4=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getExecBlockAccess().getSemicolonKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExecBlock"


    // $ANTLR start "entryRulePath"
    // InternalAvalla.g:716:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalAvalla.g:716:44: (iv_rulePath= rulePath EOF )
            // InternalAvalla.g:717:2: iv_rulePath= rulePath EOF
            {
             newCompositeNode(grammarAccess.getPathRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePath=rulePath();

            state._fsp--;

             current =iv_rulePath.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePath"


    // $ANTLR start "rulePath"
    // InternalAvalla.g:723:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+ | this_STRING_2= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_GOOD_CHARS_NO_COLON_0=null;
        Token kw=null;
        Token this_STRING_2=null;


        	enterRule();

        try {
            // InternalAvalla.g:729:2: ( ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+ | this_STRING_2= RULE_STRING ) )
            // InternalAvalla.g:730:2: ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+ | this_STRING_2= RULE_STRING )
            {
            // InternalAvalla.g:730:2: ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+ | this_STRING_2= RULE_STRING )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_GOOD_CHARS_NO_COLON||LA8_0==13) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_STRING) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalAvalla.g:731:3: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+
                    {
                    // InternalAvalla.g:731:3: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==RULE_GOOD_CHARS_NO_COLON) ) {
                            alt7=1;
                        }
                        else if ( (LA7_0==13) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalAvalla.g:732:4: this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON
                    	    {
                    	    this_GOOD_CHARS_NO_COLON_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_15); 

                    	    				current.merge(this_GOOD_CHARS_NO_COLON_0);
                    	    			

                    	    				newLeafNode(this_GOOD_CHARS_NO_COLON_0, grammarAccess.getPathAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_0_0());
                    	    			

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalAvalla.g:740:4: kw= ':'
                    	    {
                    	    kw=(Token)match(input,13,FOLLOW_15); 

                    	    				current.merge(kw);
                    	    				newLeafNode(kw, grammarAccess.getPathAccess().getColonKeyword_0_1());
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:747:3: this_STRING_2= RULE_STRING
                    {
                    this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_2);
                    		

                    			newLeafNode(this_STRING_2, grammarAccess.getPathAccess().getSTRINGTerminalRuleCall_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePath"


    // $ANTLR start "entryRulesentence"
    // InternalAvalla.g:758:1: entryRulesentence returns [String current=null] : iv_rulesentence= rulesentence EOF ;
    public final String entryRulesentence() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesentence = null;


        try {
            // InternalAvalla.g:758:48: (iv_rulesentence= rulesentence EOF )
            // InternalAvalla.g:759:2: iv_rulesentence= rulesentence EOF
            {
             newCompositeNode(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_1);
            iv_rulesentence=rulesentence();

            state._fsp--;

             current =iv_rulesentence.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesentence"


    // $ANTLR start "rulesentence"
    // InternalAvalla.g:765:1: rulesentence returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | this_STRING_1= RULE_STRING )+ ;
    public final AntlrDatatypeRuleToken rulesentence() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_GOOD_CHARS_NO_COLON_0=null;
        Token this_STRING_1=null;


        	enterRule();

        try {
            // InternalAvalla.g:771:2: ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | this_STRING_1= RULE_STRING )+ )
            // InternalAvalla.g:772:2: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | this_STRING_1= RULE_STRING )+
            {
            // InternalAvalla.g:772:2: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | this_STRING_1= RULE_STRING )+
            int cnt9=0;
            loop9:
            do {
                int alt9=3;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_GOOD_CHARS_NO_COLON) ) {
                    alt9=1;
                }
                else if ( (LA9_0==RULE_STRING) ) {
                    alt9=2;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalAvalla.g:773:3: this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON
            	    {
            	    this_GOOD_CHARS_NO_COLON_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_16); 

            	    			current.merge(this_GOOD_CHARS_NO_COLON_0);
            	    		

            	    			newLeafNode(this_GOOD_CHARS_NO_COLON_0, grammarAccess.getSentenceAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_0());
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAvalla.g:781:3: this_STRING_1= RULE_STRING
            	    {
            	    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_16); 

            	    			current.merge(this_STRING_1);
            	    		

            	    			newLeafNode(this_STRING_1, grammarAccess.getSentenceAccess().getSTRINGTerminalRuleCall_1());
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulesentence"


    // $ANTLR start "entryRulesentencePlusAssign"
    // InternalAvalla.g:792:1: entryRulesentencePlusAssign returns [String current=null] : iv_rulesentencePlusAssign= rulesentencePlusAssign EOF ;
    public final String entryRulesentencePlusAssign() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesentencePlusAssign = null;


        try {
            // InternalAvalla.g:792:58: (iv_rulesentencePlusAssign= rulesentencePlusAssign EOF )
            // InternalAvalla.g:793:2: iv_rulesentencePlusAssign= rulesentencePlusAssign EOF
            {
             newCompositeNode(grammarAccess.getSentencePlusAssignRule()); 
            pushFollow(FOLLOW_1);
            iv_rulesentencePlusAssign=rulesentencePlusAssign();

            state._fsp--;

             current =iv_rulesentencePlusAssign.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesentencePlusAssign"


    // $ANTLR start "rulesentencePlusAssign"
    // InternalAvalla.g:799:1: rulesentencePlusAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':=' | this_STRING_2= RULE_STRING )+ ;
    public final AntlrDatatypeRuleToken rulesentencePlusAssign() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_GOOD_CHARS_NO_COLON_0=null;
        Token kw=null;
        Token this_STRING_2=null;


        	enterRule();

        try {
            // InternalAvalla.g:805:2: ( (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':=' | this_STRING_2= RULE_STRING )+ )
            // InternalAvalla.g:806:2: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':=' | this_STRING_2= RULE_STRING )+
            {
            // InternalAvalla.g:806:2: (this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON | kw= ':=' | this_STRING_2= RULE_STRING )+
            int cnt10=0;
            loop10:
            do {
                int alt10=4;
                switch ( input.LA(1) ) {
                case RULE_GOOD_CHARS_NO_COLON:
                    {
                    alt10=1;
                    }
                    break;
                case 17:
                    {
                    alt10=2;
                    }
                    break;
                case RULE_STRING:
                    {
                    alt10=3;
                    }
                    break;

                }

                switch (alt10) {
            	case 1 :
            	    // InternalAvalla.g:807:3: this_GOOD_CHARS_NO_COLON_0= RULE_GOOD_CHARS_NO_COLON
            	    {
            	    this_GOOD_CHARS_NO_COLON_0=(Token)match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_17); 

            	    			current.merge(this_GOOD_CHARS_NO_COLON_0);
            	    		

            	    			newLeafNode(this_GOOD_CHARS_NO_COLON_0, grammarAccess.getSentencePlusAssignAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_0());
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAvalla.g:815:3: kw= ':='
            	    {
            	    kw=(Token)match(input,17,FOLLOW_17); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getSentencePlusAssignAccess().getColonEqualsSignKeyword_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalAvalla.g:821:3: this_STRING_2= RULE_STRING
            	    {
            	    this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_17); 

            	    			current.merge(this_STRING_2);
            	    		

            	    			newLeafNode(this_STRING_2, grammarAccess.getSentencePlusAssignAccess().getSTRINGTerminalRuleCall_2());
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulesentencePlusAssign"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002030L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000B59002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000B58002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000F58000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000002012L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000020032L});

}