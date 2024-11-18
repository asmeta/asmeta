package org.asmeta.avallaxt.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.asmeta.avallaxt.services.AvallaGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAvallaParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_GOOD_CHARS_NO_COLON", "RULE_LOCAL_VARIABLE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_GOOD_CHAR_NO_COLON", "'step'", "':'", "':='", "'scenario'", "'load'", "'invariant'", "';'", "'check'", "'set'", "'until'", "'exec'", "'setchoose'", "'begin'", "'end'", "'execblock'"
    };
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
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
    public static final int RULE_GOOD_CHARS_NO_COLON=5;
    public static final int RULE_WS=9;
    public static final int RULE_LOCAL_VARIABLE=6;
    public static final int RULE_GOOD_CHAR_NO_COLON=10;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
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

    	public void setGrammarAccess(AvallaGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleScenario"
    // InternalAvalla.g:53:1: entryRuleScenario : ruleScenario EOF ;
    public final void entryRuleScenario() throws RecognitionException {
        try {
            // InternalAvalla.g:54:1: ( ruleScenario EOF )
            // InternalAvalla.g:55:1: ruleScenario EOF
            {
             before(grammarAccess.getScenarioRule()); 
            pushFollow(FOLLOW_1);
            ruleScenario();

            state._fsp--;

             after(grammarAccess.getScenarioRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleScenario"


    // $ANTLR start "ruleScenario"
    // InternalAvalla.g:62:1: ruleScenario : ( ( rule__Scenario__Group__0 ) ) ;
    public final void ruleScenario() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:66:2: ( ( ( rule__Scenario__Group__0 ) ) )
            // InternalAvalla.g:67:2: ( ( rule__Scenario__Group__0 ) )
            {
            // InternalAvalla.g:67:2: ( ( rule__Scenario__Group__0 ) )
            // InternalAvalla.g:68:3: ( rule__Scenario__Group__0 )
            {
             before(grammarAccess.getScenarioAccess().getGroup()); 
            // InternalAvalla.g:69:3: ( rule__Scenario__Group__0 )
            // InternalAvalla.g:69:4: rule__Scenario__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Scenario__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getScenarioAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleScenario"


    // $ANTLR start "entryRuleInvariant"
    // InternalAvalla.g:78:1: entryRuleInvariant : ruleInvariant EOF ;
    public final void entryRuleInvariant() throws RecognitionException {
        try {
            // InternalAvalla.g:79:1: ( ruleInvariant EOF )
            // InternalAvalla.g:80:1: ruleInvariant EOF
            {
             before(grammarAccess.getInvariantRule()); 
            pushFollow(FOLLOW_1);
            ruleInvariant();

            state._fsp--;

             after(grammarAccess.getInvariantRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleInvariant"


    // $ANTLR start "ruleInvariant"
    // InternalAvalla.g:87:1: ruleInvariant : ( ( rule__Invariant__Group__0 ) ) ;
    public final void ruleInvariant() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:91:2: ( ( ( rule__Invariant__Group__0 ) ) )
            // InternalAvalla.g:92:2: ( ( rule__Invariant__Group__0 ) )
            {
            // InternalAvalla.g:92:2: ( ( rule__Invariant__Group__0 ) )
            // InternalAvalla.g:93:3: ( rule__Invariant__Group__0 )
            {
             before(grammarAccess.getInvariantAccess().getGroup()); 
            // InternalAvalla.g:94:3: ( rule__Invariant__Group__0 )
            // InternalAvalla.g:94:4: rule__Invariant__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Invariant__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getInvariantAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleInvariant"


    // $ANTLR start "entryRuleElement"
    // InternalAvalla.g:103:1: entryRuleElement : ruleElement EOF ;
    public final void entryRuleElement() throws RecognitionException {
        try {
            // InternalAvalla.g:104:1: ( ruleElement EOF )
            // InternalAvalla.g:105:1: ruleElement EOF
            {
             before(grammarAccess.getElementRule()); 
            pushFollow(FOLLOW_1);
            ruleElement();

            state._fsp--;

             after(grammarAccess.getElementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleElement"


    // $ANTLR start "ruleElement"
    // InternalAvalla.g:112:1: ruleElement : ( ( rule__Element__Alternatives ) ) ;
    public final void ruleElement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:116:2: ( ( ( rule__Element__Alternatives ) ) )
            // InternalAvalla.g:117:2: ( ( rule__Element__Alternatives ) )
            {
            // InternalAvalla.g:117:2: ( ( rule__Element__Alternatives ) )
            // InternalAvalla.g:118:3: ( rule__Element__Alternatives )
            {
             before(grammarAccess.getElementAccess().getAlternatives()); 
            // InternalAvalla.g:119:3: ( rule__Element__Alternatives )
            // InternalAvalla.g:119:4: rule__Element__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Element__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getElementAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleElement"


    // $ANTLR start "entryRuleCommand"
    // InternalAvalla.g:128:1: entryRuleCommand : ruleCommand EOF ;
    public final void entryRuleCommand() throws RecognitionException {
        try {
            // InternalAvalla.g:129:1: ( ruleCommand EOF )
            // InternalAvalla.g:130:1: ruleCommand EOF
            {
             before(grammarAccess.getCommandRule()); 
            pushFollow(FOLLOW_1);
            ruleCommand();

            state._fsp--;

             after(grammarAccess.getCommandRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCommand"


    // $ANTLR start "ruleCommand"
    // InternalAvalla.g:137:1: ruleCommand : ( ( rule__Command__Alternatives ) ) ;
    public final void ruleCommand() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:141:2: ( ( ( rule__Command__Alternatives ) ) )
            // InternalAvalla.g:142:2: ( ( rule__Command__Alternatives ) )
            {
            // InternalAvalla.g:142:2: ( ( rule__Command__Alternatives ) )
            // InternalAvalla.g:143:3: ( rule__Command__Alternatives )
            {
             before(grammarAccess.getCommandAccess().getAlternatives()); 
            // InternalAvalla.g:144:3: ( rule__Command__Alternatives )
            // InternalAvalla.g:144:4: rule__Command__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Command__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCommandAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCommand"


    // $ANTLR start "entryRuleCheck"
    // InternalAvalla.g:153:1: entryRuleCheck : ruleCheck EOF ;
    public final void entryRuleCheck() throws RecognitionException {
        try {
            // InternalAvalla.g:154:1: ( ruleCheck EOF )
            // InternalAvalla.g:155:1: ruleCheck EOF
            {
             before(grammarAccess.getCheckRule()); 
            pushFollow(FOLLOW_1);
            ruleCheck();

            state._fsp--;

             after(grammarAccess.getCheckRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCheck"


    // $ANTLR start "ruleCheck"
    // InternalAvalla.g:162:1: ruleCheck : ( ( rule__Check__Group__0 ) ) ;
    public final void ruleCheck() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:166:2: ( ( ( rule__Check__Group__0 ) ) )
            // InternalAvalla.g:167:2: ( ( rule__Check__Group__0 ) )
            {
            // InternalAvalla.g:167:2: ( ( rule__Check__Group__0 ) )
            // InternalAvalla.g:168:3: ( rule__Check__Group__0 )
            {
             before(grammarAccess.getCheckAccess().getGroup()); 
            // InternalAvalla.g:169:3: ( rule__Check__Group__0 )
            // InternalAvalla.g:169:4: rule__Check__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Check__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCheckAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCheck"


    // $ANTLR start "entryRuleSet"
    // InternalAvalla.g:178:1: entryRuleSet : ruleSet EOF ;
    public final void entryRuleSet() throws RecognitionException {
        try {
            // InternalAvalla.g:179:1: ( ruleSet EOF )
            // InternalAvalla.g:180:1: ruleSet EOF
            {
             before(grammarAccess.getSetRule()); 
            pushFollow(FOLLOW_1);
            ruleSet();

            state._fsp--;

             after(grammarAccess.getSetRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSet"


    // $ANTLR start "ruleSet"
    // InternalAvalla.g:187:1: ruleSet : ( ( rule__Set__Group__0 ) ) ;
    public final void ruleSet() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:191:2: ( ( ( rule__Set__Group__0 ) ) )
            // InternalAvalla.g:192:2: ( ( rule__Set__Group__0 ) )
            {
            // InternalAvalla.g:192:2: ( ( rule__Set__Group__0 ) )
            // InternalAvalla.g:193:3: ( rule__Set__Group__0 )
            {
             before(grammarAccess.getSetAccess().getGroup()); 
            // InternalAvalla.g:194:3: ( rule__Set__Group__0 )
            // InternalAvalla.g:194:4: rule__Set__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Set__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSetAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSet"


    // $ANTLR start "entryRuleStep"
    // InternalAvalla.g:203:1: entryRuleStep : ruleStep EOF ;
    public final void entryRuleStep() throws RecognitionException {
        try {
            // InternalAvalla.g:204:1: ( ruleStep EOF )
            // InternalAvalla.g:205:1: ruleStep EOF
            {
             before(grammarAccess.getStepRule()); 
            pushFollow(FOLLOW_1);
            ruleStep();

            state._fsp--;

             after(grammarAccess.getStepRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStep"


    // $ANTLR start "ruleStep"
    // InternalAvalla.g:212:1: ruleStep : ( 'step' ) ;
    public final void ruleStep() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:216:2: ( ( 'step' ) )
            // InternalAvalla.g:217:2: ( 'step' )
            {
            // InternalAvalla.g:217:2: ( 'step' )
            // InternalAvalla.g:218:3: 'step'
            {
             before(grammarAccess.getStepAccess().getStepKeyword()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getStepAccess().getStepKeyword()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStep"


    // $ANTLR start "entryRuleStepUntil"
    // InternalAvalla.g:228:1: entryRuleStepUntil : ruleStepUntil EOF ;
    public final void entryRuleStepUntil() throws RecognitionException {
        try {
            // InternalAvalla.g:229:1: ( ruleStepUntil EOF )
            // InternalAvalla.g:230:1: ruleStepUntil EOF
            {
             before(grammarAccess.getStepUntilRule()); 
            pushFollow(FOLLOW_1);
            ruleStepUntil();

            state._fsp--;

             after(grammarAccess.getStepUntilRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStepUntil"


    // $ANTLR start "ruleStepUntil"
    // InternalAvalla.g:237:1: ruleStepUntil : ( ( rule__StepUntil__Group__0 ) ) ;
    public final void ruleStepUntil() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:241:2: ( ( ( rule__StepUntil__Group__0 ) ) )
            // InternalAvalla.g:242:2: ( ( rule__StepUntil__Group__0 ) )
            {
            // InternalAvalla.g:242:2: ( ( rule__StepUntil__Group__0 ) )
            // InternalAvalla.g:243:3: ( rule__StepUntil__Group__0 )
            {
             before(grammarAccess.getStepUntilAccess().getGroup()); 
            // InternalAvalla.g:244:3: ( rule__StepUntil__Group__0 )
            // InternalAvalla.g:244:4: rule__StepUntil__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__StepUntil__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStepUntilAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStepUntil"


    // $ANTLR start "entryRuleExec"
    // InternalAvalla.g:253:1: entryRuleExec : ruleExec EOF ;
    public final void entryRuleExec() throws RecognitionException {
        try {
            // InternalAvalla.g:254:1: ( ruleExec EOF )
            // InternalAvalla.g:255:1: ruleExec EOF
            {
             before(grammarAccess.getExecRule()); 
            pushFollow(FOLLOW_1);
            ruleExec();

            state._fsp--;

             after(grammarAccess.getExecRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleExec"


    // $ANTLR start "ruleExec"
    // InternalAvalla.g:262:1: ruleExec : ( ( rule__Exec__Group__0 ) ) ;
    public final void ruleExec() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:266:2: ( ( ( rule__Exec__Group__0 ) ) )
            // InternalAvalla.g:267:2: ( ( rule__Exec__Group__0 ) )
            {
            // InternalAvalla.g:267:2: ( ( rule__Exec__Group__0 ) )
            // InternalAvalla.g:268:3: ( rule__Exec__Group__0 )
            {
             before(grammarAccess.getExecAccess().getGroup()); 
            // InternalAvalla.g:269:3: ( rule__Exec__Group__0 )
            // InternalAvalla.g:269:4: rule__Exec__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Exec__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExecAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExec"


    // $ANTLR start "entryRuleChoose"
    // InternalAvalla.g:278:1: entryRuleChoose : ruleChoose EOF ;
    public final void entryRuleChoose() throws RecognitionException {
        try {
            // InternalAvalla.g:279:1: ( ruleChoose EOF )
            // InternalAvalla.g:280:1: ruleChoose EOF
            {
             before(grammarAccess.getChooseRule()); 
            pushFollow(FOLLOW_1);
            ruleChoose();

            state._fsp--;

             after(grammarAccess.getChooseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleChoose"


    // $ANTLR start "ruleChoose"
    // InternalAvalla.g:287:1: ruleChoose : ( ( rule__Choose__Group__0 ) ) ;
    public final void ruleChoose() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:291:2: ( ( ( rule__Choose__Group__0 ) ) )
            // InternalAvalla.g:292:2: ( ( rule__Choose__Group__0 ) )
            {
            // InternalAvalla.g:292:2: ( ( rule__Choose__Group__0 ) )
            // InternalAvalla.g:293:3: ( rule__Choose__Group__0 )
            {
             before(grammarAccess.getChooseAccess().getGroup()); 
            // InternalAvalla.g:294:3: ( rule__Choose__Group__0 )
            // InternalAvalla.g:294:4: rule__Choose__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Choose__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getChooseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleChoose"


    // $ANTLR start "entryRuleBlock"
    // InternalAvalla.g:303:1: entryRuleBlock : ruleBlock EOF ;
    public final void entryRuleBlock() throws RecognitionException {
        try {
            // InternalAvalla.g:304:1: ( ruleBlock EOF )
            // InternalAvalla.g:305:1: ruleBlock EOF
            {
             before(grammarAccess.getBlockRule()); 
            pushFollow(FOLLOW_1);
            ruleBlock();

            state._fsp--;

             after(grammarAccess.getBlockRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBlock"


    // $ANTLR start "ruleBlock"
    // InternalAvalla.g:312:1: ruleBlock : ( ( rule__Block__Group__0 ) ) ;
    public final void ruleBlock() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:316:2: ( ( ( rule__Block__Group__0 ) ) )
            // InternalAvalla.g:317:2: ( ( rule__Block__Group__0 ) )
            {
            // InternalAvalla.g:317:2: ( ( rule__Block__Group__0 ) )
            // InternalAvalla.g:318:3: ( rule__Block__Group__0 )
            {
             before(grammarAccess.getBlockAccess().getGroup()); 
            // InternalAvalla.g:319:3: ( rule__Block__Group__0 )
            // InternalAvalla.g:319:4: rule__Block__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Block__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getBlockAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBlock"


    // $ANTLR start "entryRuleExecBlock"
    // InternalAvalla.g:328:1: entryRuleExecBlock : ruleExecBlock EOF ;
    public final void entryRuleExecBlock() throws RecognitionException {
        try {
            // InternalAvalla.g:329:1: ( ruleExecBlock EOF )
            // InternalAvalla.g:330:1: ruleExecBlock EOF
            {
             before(grammarAccess.getExecBlockRule()); 
            pushFollow(FOLLOW_1);
            ruleExecBlock();

            state._fsp--;

             after(grammarAccess.getExecBlockRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleExecBlock"


    // $ANTLR start "ruleExecBlock"
    // InternalAvalla.g:337:1: ruleExecBlock : ( ( rule__ExecBlock__Group__0 ) ) ;
    public final void ruleExecBlock() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:341:2: ( ( ( rule__ExecBlock__Group__0 ) ) )
            // InternalAvalla.g:342:2: ( ( rule__ExecBlock__Group__0 ) )
            {
            // InternalAvalla.g:342:2: ( ( rule__ExecBlock__Group__0 ) )
            // InternalAvalla.g:343:3: ( rule__ExecBlock__Group__0 )
            {
             before(grammarAccess.getExecBlockAccess().getGroup()); 
            // InternalAvalla.g:344:3: ( rule__ExecBlock__Group__0 )
            // InternalAvalla.g:344:4: rule__ExecBlock__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExecBlockAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExecBlock"


    // $ANTLR start "entryRulePath"
    // InternalAvalla.g:353:1: entryRulePath : rulePath EOF ;
    public final void entryRulePath() throws RecognitionException {
        try {
            // InternalAvalla.g:354:1: ( rulePath EOF )
            // InternalAvalla.g:355:1: rulePath EOF
            {
             before(grammarAccess.getPathRule()); 
            pushFollow(FOLLOW_1);
            rulePath();

            state._fsp--;

             after(grammarAccess.getPathRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePath"


    // $ANTLR start "rulePath"
    // InternalAvalla.g:362:1: rulePath : ( ( rule__Path__Alternatives ) ) ;
    public final void rulePath() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:366:2: ( ( ( rule__Path__Alternatives ) ) )
            // InternalAvalla.g:367:2: ( ( rule__Path__Alternatives ) )
            {
            // InternalAvalla.g:367:2: ( ( rule__Path__Alternatives ) )
            // InternalAvalla.g:368:3: ( rule__Path__Alternatives )
            {
             before(grammarAccess.getPathAccess().getAlternatives()); 
            // InternalAvalla.g:369:3: ( rule__Path__Alternatives )
            // InternalAvalla.g:369:4: rule__Path__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Path__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPathAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePath"


    // $ANTLR start "entryRulesentence"
    // InternalAvalla.g:378:1: entryRulesentence : rulesentence EOF ;
    public final void entryRulesentence() throws RecognitionException {
        try {
            // InternalAvalla.g:379:1: ( rulesentence EOF )
            // InternalAvalla.g:380:1: rulesentence EOF
            {
             before(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_1);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getSentenceRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulesentence"


    // $ANTLR start "rulesentence"
    // InternalAvalla.g:387:1: rulesentence : ( ( ( rule__Sentence__Alternatives ) ) ( ( rule__Sentence__Alternatives )* ) ) ;
    public final void rulesentence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:391:2: ( ( ( ( rule__Sentence__Alternatives ) ) ( ( rule__Sentence__Alternatives )* ) ) )
            // InternalAvalla.g:392:2: ( ( ( rule__Sentence__Alternatives ) ) ( ( rule__Sentence__Alternatives )* ) )
            {
            // InternalAvalla.g:392:2: ( ( ( rule__Sentence__Alternatives ) ) ( ( rule__Sentence__Alternatives )* ) )
            // InternalAvalla.g:393:3: ( ( rule__Sentence__Alternatives ) ) ( ( rule__Sentence__Alternatives )* )
            {
            // InternalAvalla.g:393:3: ( ( rule__Sentence__Alternatives ) )
            // InternalAvalla.g:394:4: ( rule__Sentence__Alternatives )
            {
             before(grammarAccess.getSentenceAccess().getAlternatives()); 
            // InternalAvalla.g:395:4: ( rule__Sentence__Alternatives )
            // InternalAvalla.g:395:5: rule__Sentence__Alternatives
            {
            pushFollow(FOLLOW_3);
            rule__Sentence__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getAlternatives()); 

            }

            // InternalAvalla.g:398:3: ( ( rule__Sentence__Alternatives )* )
            // InternalAvalla.g:399:4: ( rule__Sentence__Alternatives )*
            {
             before(grammarAccess.getSentenceAccess().getAlternatives()); 
            // InternalAvalla.g:400:4: ( rule__Sentence__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_STRING && LA1_0<=RULE_LOCAL_VARIABLE)||LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAvalla.g:400:5: rule__Sentence__Alternatives
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Sentence__Alternatives();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getSentenceAccess().getAlternatives()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulesentence"


    // $ANTLR start "entryRulesentencePlusAssignAndColon"
    // InternalAvalla.g:410:1: entryRulesentencePlusAssignAndColon : rulesentencePlusAssignAndColon EOF ;
    public final void entryRulesentencePlusAssignAndColon() throws RecognitionException {
        try {
            // InternalAvalla.g:411:1: ( rulesentencePlusAssignAndColon EOF )
            // InternalAvalla.g:412:1: rulesentencePlusAssignAndColon EOF
            {
             before(grammarAccess.getSentencePlusAssignAndColonRule()); 
            pushFollow(FOLLOW_1);
            rulesentencePlusAssignAndColon();

            state._fsp--;

             after(grammarAccess.getSentencePlusAssignAndColonRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulesentencePlusAssignAndColon"


    // $ANTLR start "rulesentencePlusAssignAndColon"
    // InternalAvalla.g:419:1: rulesentencePlusAssignAndColon : ( ( ( rule__SentencePlusAssignAndColon__Alternatives ) ) ( ( rule__SentencePlusAssignAndColon__Alternatives )* ) ) ;
    public final void rulesentencePlusAssignAndColon() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:423:2: ( ( ( ( rule__SentencePlusAssignAndColon__Alternatives ) ) ( ( rule__SentencePlusAssignAndColon__Alternatives )* ) ) )
            // InternalAvalla.g:424:2: ( ( ( rule__SentencePlusAssignAndColon__Alternatives ) ) ( ( rule__SentencePlusAssignAndColon__Alternatives )* ) )
            {
            // InternalAvalla.g:424:2: ( ( ( rule__SentencePlusAssignAndColon__Alternatives ) ) ( ( rule__SentencePlusAssignAndColon__Alternatives )* ) )
            // InternalAvalla.g:425:3: ( ( rule__SentencePlusAssignAndColon__Alternatives ) ) ( ( rule__SentencePlusAssignAndColon__Alternatives )* )
            {
            // InternalAvalla.g:425:3: ( ( rule__SentencePlusAssignAndColon__Alternatives ) )
            // InternalAvalla.g:426:4: ( rule__SentencePlusAssignAndColon__Alternatives )
            {
             before(grammarAccess.getSentencePlusAssignAndColonAccess().getAlternatives()); 
            // InternalAvalla.g:427:4: ( rule__SentencePlusAssignAndColon__Alternatives )
            // InternalAvalla.g:427:5: rule__SentencePlusAssignAndColon__Alternatives
            {
            pushFollow(FOLLOW_4);
            rule__SentencePlusAssignAndColon__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSentencePlusAssignAndColonAccess().getAlternatives()); 

            }

            // InternalAvalla.g:430:3: ( ( rule__SentencePlusAssignAndColon__Alternatives )* )
            // InternalAvalla.g:431:4: ( rule__SentencePlusAssignAndColon__Alternatives )*
            {
             before(grammarAccess.getSentencePlusAssignAndColonAccess().getAlternatives()); 
            // InternalAvalla.g:432:4: ( rule__SentencePlusAssignAndColon__Alternatives )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=RULE_STRING && LA2_0<=RULE_LOCAL_VARIABLE)||(LA2_0>=12 && LA2_0<=13)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalAvalla.g:432:5: rule__SentencePlusAssignAndColon__Alternatives
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__SentencePlusAssignAndColon__Alternatives();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

             after(grammarAccess.getSentencePlusAssignAndColonAccess().getAlternatives()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulesentencePlusAssignAndColon"


    // $ANTLR start "rule__Element__Alternatives"
    // InternalAvalla.g:441:1: rule__Element__Alternatives : ( ( ruleCommand ) | ( ruleBlock ) );
    public final void rule__Element__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:445:1: ( ( ruleCommand ) | ( ruleBlock ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==11||(LA3_0>=18 && LA3_0<=19)||(LA3_0>=21 && LA3_0<=22)||LA3_0==25) ) {
                alt3=1;
            }
            else if ( (LA3_0==23) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalAvalla.g:446:2: ( ruleCommand )
                    {
                    // InternalAvalla.g:446:2: ( ruleCommand )
                    // InternalAvalla.g:447:3: ruleCommand
                    {
                     before(grammarAccess.getElementAccess().getCommandParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleCommand();

                    state._fsp--;

                     after(grammarAccess.getElementAccess().getCommandParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:452:2: ( ruleBlock )
                    {
                    // InternalAvalla.g:452:2: ( ruleBlock )
                    // InternalAvalla.g:453:3: ruleBlock
                    {
                     before(grammarAccess.getElementAccess().getBlockParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleBlock();

                    state._fsp--;

                     after(grammarAccess.getElementAccess().getBlockParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Element__Alternatives"


    // $ANTLR start "rule__Command__Alternatives"
    // InternalAvalla.g:462:1: rule__Command__Alternatives : ( ( ruleCheck ) | ( ruleSet ) | ( ( rule__Command__Group_2__0 ) ) | ( ruleStepUntil ) | ( ruleExec ) | ( ruleExecBlock ) | ( ruleChoose ) );
    public final void rule__Command__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:466:1: ( ( ruleCheck ) | ( ruleSet ) | ( ( rule__Command__Group_2__0 ) ) | ( ruleStepUntil ) | ( ruleExec ) | ( ruleExecBlock ) | ( ruleChoose ) )
            int alt4=7;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt4=1;
                }
                break;
            case 19:
                {
                alt4=2;
                }
                break;
            case 11:
                {
                int LA4_3 = input.LA(2);

                if ( (LA4_3==EOF||LA4_3==11||(LA4_3>=18 && LA4_3<=19)||(LA4_3>=21 && LA4_3<=25)) ) {
                    alt4=3;
                }
                else if ( (LA4_3==20) ) {
                    alt4=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 3, input);

                    throw nvae;
                }
                }
                break;
            case 21:
                {
                alt4=5;
                }
                break;
            case 25:
                {
                alt4=6;
                }
                break;
            case 22:
                {
                alt4=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalAvalla.g:467:2: ( ruleCheck )
                    {
                    // InternalAvalla.g:467:2: ( ruleCheck )
                    // InternalAvalla.g:468:3: ruleCheck
                    {
                     before(grammarAccess.getCommandAccess().getCheckParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleCheck();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getCheckParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:473:2: ( ruleSet )
                    {
                    // InternalAvalla.g:473:2: ( ruleSet )
                    // InternalAvalla.g:474:3: ruleSet
                    {
                     before(grammarAccess.getCommandAccess().getSetParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleSet();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getSetParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalAvalla.g:479:2: ( ( rule__Command__Group_2__0 ) )
                    {
                    // InternalAvalla.g:479:2: ( ( rule__Command__Group_2__0 ) )
                    // InternalAvalla.g:480:3: ( rule__Command__Group_2__0 )
                    {
                     before(grammarAccess.getCommandAccess().getGroup_2()); 
                    // InternalAvalla.g:481:3: ( rule__Command__Group_2__0 )
                    // InternalAvalla.g:481:4: rule__Command__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Command__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getCommandAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalAvalla.g:485:2: ( ruleStepUntil )
                    {
                    // InternalAvalla.g:485:2: ( ruleStepUntil )
                    // InternalAvalla.g:486:3: ruleStepUntil
                    {
                     before(grammarAccess.getCommandAccess().getStepUntilParserRuleCall_3()); 
                    pushFollow(FOLLOW_2);
                    ruleStepUntil();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getStepUntilParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalAvalla.g:491:2: ( ruleExec )
                    {
                    // InternalAvalla.g:491:2: ( ruleExec )
                    // InternalAvalla.g:492:3: ruleExec
                    {
                     before(grammarAccess.getCommandAccess().getExecParserRuleCall_4()); 
                    pushFollow(FOLLOW_2);
                    ruleExec();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getExecParserRuleCall_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalAvalla.g:497:2: ( ruleExecBlock )
                    {
                    // InternalAvalla.g:497:2: ( ruleExecBlock )
                    // InternalAvalla.g:498:3: ruleExecBlock
                    {
                     before(grammarAccess.getCommandAccess().getExecBlockParserRuleCall_5()); 
                    pushFollow(FOLLOW_2);
                    ruleExecBlock();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getExecBlockParserRuleCall_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalAvalla.g:503:2: ( ruleChoose )
                    {
                    // InternalAvalla.g:503:2: ( ruleChoose )
                    // InternalAvalla.g:504:3: ruleChoose
                    {
                     before(grammarAccess.getCommandAccess().getChooseParserRuleCall_6()); 
                    pushFollow(FOLLOW_2);
                    ruleChoose();

                    state._fsp--;

                     after(grammarAccess.getCommandAccess().getChooseParserRuleCall_6()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Command__Alternatives"


    // $ANTLR start "rule__Path__Alternatives"
    // InternalAvalla.g:513:1: rule__Path__Alternatives : ( ( ( ( rule__Path__Alternatives_0 ) ) ( ( rule__Path__Alternatives_0 )* ) ) | ( RULE_STRING ) );
    public final void rule__Path__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:517:1: ( ( ( ( rule__Path__Alternatives_0 ) ) ( ( rule__Path__Alternatives_0 )* ) ) | ( RULE_STRING ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_GOOD_CHARS_NO_COLON||LA6_0==12) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_STRING) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalAvalla.g:518:2: ( ( ( rule__Path__Alternatives_0 ) ) ( ( rule__Path__Alternatives_0 )* ) )
                    {
                    // InternalAvalla.g:518:2: ( ( ( rule__Path__Alternatives_0 ) ) ( ( rule__Path__Alternatives_0 )* ) )
                    // InternalAvalla.g:519:3: ( ( rule__Path__Alternatives_0 ) ) ( ( rule__Path__Alternatives_0 )* )
                    {
                    // InternalAvalla.g:519:3: ( ( rule__Path__Alternatives_0 ) )
                    // InternalAvalla.g:520:4: ( rule__Path__Alternatives_0 )
                    {
                     before(grammarAccess.getPathAccess().getAlternatives_0()); 
                    // InternalAvalla.g:521:4: ( rule__Path__Alternatives_0 )
                    // InternalAvalla.g:521:5: rule__Path__Alternatives_0
                    {
                    pushFollow(FOLLOW_5);
                    rule__Path__Alternatives_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getPathAccess().getAlternatives_0()); 

                    }

                    // InternalAvalla.g:524:3: ( ( rule__Path__Alternatives_0 )* )
                    // InternalAvalla.g:525:4: ( rule__Path__Alternatives_0 )*
                    {
                     before(grammarAccess.getPathAccess().getAlternatives_0()); 
                    // InternalAvalla.g:526:4: ( rule__Path__Alternatives_0 )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==RULE_GOOD_CHARS_NO_COLON||LA5_0==12) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalAvalla.g:526:5: rule__Path__Alternatives_0
                    	    {
                    	    pushFollow(FOLLOW_5);
                    	    rule__Path__Alternatives_0();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                     after(grammarAccess.getPathAccess().getAlternatives_0()); 

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:531:2: ( RULE_STRING )
                    {
                    // InternalAvalla.g:531:2: ( RULE_STRING )
                    // InternalAvalla.g:532:3: RULE_STRING
                    {
                     before(grammarAccess.getPathAccess().getSTRINGTerminalRuleCall_1()); 
                    match(input,RULE_STRING,FOLLOW_2); 
                     after(grammarAccess.getPathAccess().getSTRINGTerminalRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Path__Alternatives"


    // $ANTLR start "rule__Path__Alternatives_0"
    // InternalAvalla.g:541:1: rule__Path__Alternatives_0 : ( ( RULE_GOOD_CHARS_NO_COLON ) | ( ':' ) );
    public final void rule__Path__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:545:1: ( ( RULE_GOOD_CHARS_NO_COLON ) | ( ':' ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_GOOD_CHARS_NO_COLON) ) {
                alt7=1;
            }
            else if ( (LA7_0==12) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalAvalla.g:546:2: ( RULE_GOOD_CHARS_NO_COLON )
                    {
                    // InternalAvalla.g:546:2: ( RULE_GOOD_CHARS_NO_COLON )
                    // InternalAvalla.g:547:3: RULE_GOOD_CHARS_NO_COLON
                    {
                     before(grammarAccess.getPathAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_0_0()); 
                    match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
                     after(grammarAccess.getPathAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:552:2: ( ':' )
                    {
                    // InternalAvalla.g:552:2: ( ':' )
                    // InternalAvalla.g:553:3: ':'
                    {
                     before(grammarAccess.getPathAccess().getColonKeyword_0_1()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getPathAccess().getColonKeyword_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Path__Alternatives_0"


    // $ANTLR start "rule__Sentence__Alternatives"
    // InternalAvalla.g:562:1: rule__Sentence__Alternatives : ( ( RULE_LOCAL_VARIABLE ) | ( RULE_GOOD_CHARS_NO_COLON ) | ( ':' ) | ( RULE_STRING ) );
    public final void rule__Sentence__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:566:1: ( ( RULE_LOCAL_VARIABLE ) | ( RULE_GOOD_CHARS_NO_COLON ) | ( ':' ) | ( RULE_STRING ) )
            int alt8=4;
            switch ( input.LA(1) ) {
            case RULE_LOCAL_VARIABLE:
                {
                alt8=1;
                }
                break;
            case RULE_GOOD_CHARS_NO_COLON:
                {
                alt8=2;
                }
                break;
            case 12:
                {
                alt8=3;
                }
                break;
            case RULE_STRING:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalAvalla.g:567:2: ( RULE_LOCAL_VARIABLE )
                    {
                    // InternalAvalla.g:567:2: ( RULE_LOCAL_VARIABLE )
                    // InternalAvalla.g:568:3: RULE_LOCAL_VARIABLE
                    {
                     before(grammarAccess.getSentenceAccess().getLOCAL_VARIABLETerminalRuleCall_0()); 
                    match(input,RULE_LOCAL_VARIABLE,FOLLOW_2); 
                     after(grammarAccess.getSentenceAccess().getLOCAL_VARIABLETerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:573:2: ( RULE_GOOD_CHARS_NO_COLON )
                    {
                    // InternalAvalla.g:573:2: ( RULE_GOOD_CHARS_NO_COLON )
                    // InternalAvalla.g:574:3: RULE_GOOD_CHARS_NO_COLON
                    {
                     before(grammarAccess.getSentenceAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_1()); 
                    match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
                     after(grammarAccess.getSentenceAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalAvalla.g:579:2: ( ':' )
                    {
                    // InternalAvalla.g:579:2: ( ':' )
                    // InternalAvalla.g:580:3: ':'
                    {
                     before(grammarAccess.getSentenceAccess().getColonKeyword_2()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getSentenceAccess().getColonKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalAvalla.g:585:2: ( RULE_STRING )
                    {
                    // InternalAvalla.g:585:2: ( RULE_STRING )
                    // InternalAvalla.g:586:3: RULE_STRING
                    {
                     before(grammarAccess.getSentenceAccess().getSTRINGTerminalRuleCall_3()); 
                    match(input,RULE_STRING,FOLLOW_2); 
                     after(grammarAccess.getSentenceAccess().getSTRINGTerminalRuleCall_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Alternatives"


    // $ANTLR start "rule__SentencePlusAssignAndColon__Alternatives"
    // InternalAvalla.g:595:1: rule__SentencePlusAssignAndColon__Alternatives : ( ( RULE_LOCAL_VARIABLE ) | ( RULE_GOOD_CHARS_NO_COLON ) | ( ':=' ) | ( ':' ) | ( RULE_STRING ) );
    public final void rule__SentencePlusAssignAndColon__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:599:1: ( ( RULE_LOCAL_VARIABLE ) | ( RULE_GOOD_CHARS_NO_COLON ) | ( ':=' ) | ( ':' ) | ( RULE_STRING ) )
            int alt9=5;
            switch ( input.LA(1) ) {
            case RULE_LOCAL_VARIABLE:
                {
                alt9=1;
                }
                break;
            case RULE_GOOD_CHARS_NO_COLON:
                {
                alt9=2;
                }
                break;
            case 13:
                {
                alt9=3;
                }
                break;
            case 12:
                {
                alt9=4;
                }
                break;
            case RULE_STRING:
                {
                alt9=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalAvalla.g:600:2: ( RULE_LOCAL_VARIABLE )
                    {
                    // InternalAvalla.g:600:2: ( RULE_LOCAL_VARIABLE )
                    // InternalAvalla.g:601:3: RULE_LOCAL_VARIABLE
                    {
                     before(grammarAccess.getSentencePlusAssignAndColonAccess().getLOCAL_VARIABLETerminalRuleCall_0()); 
                    match(input,RULE_LOCAL_VARIABLE,FOLLOW_2); 
                     after(grammarAccess.getSentencePlusAssignAndColonAccess().getLOCAL_VARIABLETerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalAvalla.g:606:2: ( RULE_GOOD_CHARS_NO_COLON )
                    {
                    // InternalAvalla.g:606:2: ( RULE_GOOD_CHARS_NO_COLON )
                    // InternalAvalla.g:607:3: RULE_GOOD_CHARS_NO_COLON
                    {
                     before(grammarAccess.getSentencePlusAssignAndColonAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_1()); 
                    match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
                     after(grammarAccess.getSentencePlusAssignAndColonAccess().getGOOD_CHARS_NO_COLONTerminalRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalAvalla.g:612:2: ( ':=' )
                    {
                    // InternalAvalla.g:612:2: ( ':=' )
                    // InternalAvalla.g:613:3: ':='
                    {
                     before(grammarAccess.getSentencePlusAssignAndColonAccess().getColonEqualsSignKeyword_2()); 
                    match(input,13,FOLLOW_2); 
                     after(grammarAccess.getSentencePlusAssignAndColonAccess().getColonEqualsSignKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalAvalla.g:618:2: ( ':' )
                    {
                    // InternalAvalla.g:618:2: ( ':' )
                    // InternalAvalla.g:619:3: ':'
                    {
                     before(grammarAccess.getSentencePlusAssignAndColonAccess().getColonKeyword_3()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getSentencePlusAssignAndColonAccess().getColonKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalAvalla.g:624:2: ( RULE_STRING )
                    {
                    // InternalAvalla.g:624:2: ( RULE_STRING )
                    // InternalAvalla.g:625:3: RULE_STRING
                    {
                     before(grammarAccess.getSentencePlusAssignAndColonAccess().getSTRINGTerminalRuleCall_4()); 
                    match(input,RULE_STRING,FOLLOW_2); 
                     after(grammarAccess.getSentencePlusAssignAndColonAccess().getSTRINGTerminalRuleCall_4()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SentencePlusAssignAndColon__Alternatives"


    // $ANTLR start "rule__Scenario__Group__0"
    // InternalAvalla.g:634:1: rule__Scenario__Group__0 : rule__Scenario__Group__0__Impl rule__Scenario__Group__1 ;
    public final void rule__Scenario__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:638:1: ( rule__Scenario__Group__0__Impl rule__Scenario__Group__1 )
            // InternalAvalla.g:639:2: rule__Scenario__Group__0__Impl rule__Scenario__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Scenario__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Scenario__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__0"


    // $ANTLR start "rule__Scenario__Group__0__Impl"
    // InternalAvalla.g:646:1: rule__Scenario__Group__0__Impl : ( 'scenario' ) ;
    public final void rule__Scenario__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:650:1: ( ( 'scenario' ) )
            // InternalAvalla.g:651:1: ( 'scenario' )
            {
            // InternalAvalla.g:651:1: ( 'scenario' )
            // InternalAvalla.g:652:2: 'scenario'
            {
             before(grammarAccess.getScenarioAccess().getScenarioKeyword_0()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getScenarioAccess().getScenarioKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__0__Impl"


    // $ANTLR start "rule__Scenario__Group__1"
    // InternalAvalla.g:661:1: rule__Scenario__Group__1 : rule__Scenario__Group__1__Impl rule__Scenario__Group__2 ;
    public final void rule__Scenario__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:665:1: ( rule__Scenario__Group__1__Impl rule__Scenario__Group__2 )
            // InternalAvalla.g:666:2: rule__Scenario__Group__1__Impl rule__Scenario__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Scenario__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Scenario__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__1"


    // $ANTLR start "rule__Scenario__Group__1__Impl"
    // InternalAvalla.g:673:1: rule__Scenario__Group__1__Impl : ( ( rule__Scenario__NameAssignment_1 ) ) ;
    public final void rule__Scenario__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:677:1: ( ( ( rule__Scenario__NameAssignment_1 ) ) )
            // InternalAvalla.g:678:1: ( ( rule__Scenario__NameAssignment_1 ) )
            {
            // InternalAvalla.g:678:1: ( ( rule__Scenario__NameAssignment_1 ) )
            // InternalAvalla.g:679:2: ( rule__Scenario__NameAssignment_1 )
            {
             before(grammarAccess.getScenarioAccess().getNameAssignment_1()); 
            // InternalAvalla.g:680:2: ( rule__Scenario__NameAssignment_1 )
            // InternalAvalla.g:680:3: rule__Scenario__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Scenario__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getScenarioAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__1__Impl"


    // $ANTLR start "rule__Scenario__Group__2"
    // InternalAvalla.g:688:1: rule__Scenario__Group__2 : rule__Scenario__Group__2__Impl rule__Scenario__Group__3 ;
    public final void rule__Scenario__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:692:1: ( rule__Scenario__Group__2__Impl rule__Scenario__Group__3 )
            // InternalAvalla.g:693:2: rule__Scenario__Group__2__Impl rule__Scenario__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__Scenario__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Scenario__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__2"


    // $ANTLR start "rule__Scenario__Group__2__Impl"
    // InternalAvalla.g:700:1: rule__Scenario__Group__2__Impl : ( 'load' ) ;
    public final void rule__Scenario__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:704:1: ( ( 'load' ) )
            // InternalAvalla.g:705:1: ( 'load' )
            {
            // InternalAvalla.g:705:1: ( 'load' )
            // InternalAvalla.g:706:2: 'load'
            {
             before(grammarAccess.getScenarioAccess().getLoadKeyword_2()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getScenarioAccess().getLoadKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__2__Impl"


    // $ANTLR start "rule__Scenario__Group__3"
    // InternalAvalla.g:715:1: rule__Scenario__Group__3 : rule__Scenario__Group__3__Impl rule__Scenario__Group__4 ;
    public final void rule__Scenario__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:719:1: ( rule__Scenario__Group__3__Impl rule__Scenario__Group__4 )
            // InternalAvalla.g:720:2: rule__Scenario__Group__3__Impl rule__Scenario__Group__4
            {
            pushFollow(FOLLOW_9);
            rule__Scenario__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Scenario__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__3"


    // $ANTLR start "rule__Scenario__Group__3__Impl"
    // InternalAvalla.g:727:1: rule__Scenario__Group__3__Impl : ( ( rule__Scenario__SpecAssignment_3 ) ) ;
    public final void rule__Scenario__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:731:1: ( ( ( rule__Scenario__SpecAssignment_3 ) ) )
            // InternalAvalla.g:732:1: ( ( rule__Scenario__SpecAssignment_3 ) )
            {
            // InternalAvalla.g:732:1: ( ( rule__Scenario__SpecAssignment_3 ) )
            // InternalAvalla.g:733:2: ( rule__Scenario__SpecAssignment_3 )
            {
             before(grammarAccess.getScenarioAccess().getSpecAssignment_3()); 
            // InternalAvalla.g:734:2: ( rule__Scenario__SpecAssignment_3 )
            // InternalAvalla.g:734:3: rule__Scenario__SpecAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Scenario__SpecAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getScenarioAccess().getSpecAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__3__Impl"


    // $ANTLR start "rule__Scenario__Group__4"
    // InternalAvalla.g:742:1: rule__Scenario__Group__4 : rule__Scenario__Group__4__Impl rule__Scenario__Group__5 ;
    public final void rule__Scenario__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:746:1: ( rule__Scenario__Group__4__Impl rule__Scenario__Group__5 )
            // InternalAvalla.g:747:2: rule__Scenario__Group__4__Impl rule__Scenario__Group__5
            {
            pushFollow(FOLLOW_9);
            rule__Scenario__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Scenario__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__4"


    // $ANTLR start "rule__Scenario__Group__4__Impl"
    // InternalAvalla.g:754:1: rule__Scenario__Group__4__Impl : ( ( rule__Scenario__InvariantsAssignment_4 )* ) ;
    public final void rule__Scenario__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:758:1: ( ( ( rule__Scenario__InvariantsAssignment_4 )* ) )
            // InternalAvalla.g:759:1: ( ( rule__Scenario__InvariantsAssignment_4 )* )
            {
            // InternalAvalla.g:759:1: ( ( rule__Scenario__InvariantsAssignment_4 )* )
            // InternalAvalla.g:760:2: ( rule__Scenario__InvariantsAssignment_4 )*
            {
             before(grammarAccess.getScenarioAccess().getInvariantsAssignment_4()); 
            // InternalAvalla.g:761:2: ( rule__Scenario__InvariantsAssignment_4 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==16) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalAvalla.g:761:3: rule__Scenario__InvariantsAssignment_4
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__Scenario__InvariantsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getScenarioAccess().getInvariantsAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__4__Impl"


    // $ANTLR start "rule__Scenario__Group__5"
    // InternalAvalla.g:769:1: rule__Scenario__Group__5 : rule__Scenario__Group__5__Impl ;
    public final void rule__Scenario__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:773:1: ( rule__Scenario__Group__5__Impl )
            // InternalAvalla.g:774:2: rule__Scenario__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Scenario__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__5"


    // $ANTLR start "rule__Scenario__Group__5__Impl"
    // InternalAvalla.g:780:1: rule__Scenario__Group__5__Impl : ( ( rule__Scenario__ElementsAssignment_5 )* ) ;
    public final void rule__Scenario__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:784:1: ( ( ( rule__Scenario__ElementsAssignment_5 )* ) )
            // InternalAvalla.g:785:1: ( ( rule__Scenario__ElementsAssignment_5 )* )
            {
            // InternalAvalla.g:785:1: ( ( rule__Scenario__ElementsAssignment_5 )* )
            // InternalAvalla.g:786:2: ( rule__Scenario__ElementsAssignment_5 )*
            {
             before(grammarAccess.getScenarioAccess().getElementsAssignment_5()); 
            // InternalAvalla.g:787:2: ( rule__Scenario__ElementsAssignment_5 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==11||(LA11_0>=18 && LA11_0<=19)||(LA11_0>=21 && LA11_0<=23)||LA11_0==25) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalAvalla.g:787:3: rule__Scenario__ElementsAssignment_5
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__Scenario__ElementsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getScenarioAccess().getElementsAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__Group__5__Impl"


    // $ANTLR start "rule__Invariant__Group__0"
    // InternalAvalla.g:796:1: rule__Invariant__Group__0 : rule__Invariant__Group__0__Impl rule__Invariant__Group__1 ;
    public final void rule__Invariant__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:800:1: ( rule__Invariant__Group__0__Impl rule__Invariant__Group__1 )
            // InternalAvalla.g:801:2: rule__Invariant__Group__0__Impl rule__Invariant__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Invariant__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Invariant__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__0"


    // $ANTLR start "rule__Invariant__Group__0__Impl"
    // InternalAvalla.g:808:1: rule__Invariant__Group__0__Impl : ( 'invariant' ) ;
    public final void rule__Invariant__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:812:1: ( ( 'invariant' ) )
            // InternalAvalla.g:813:1: ( 'invariant' )
            {
            // InternalAvalla.g:813:1: ( 'invariant' )
            // InternalAvalla.g:814:2: 'invariant'
            {
             before(grammarAccess.getInvariantAccess().getInvariantKeyword_0()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getInvariantAccess().getInvariantKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__0__Impl"


    // $ANTLR start "rule__Invariant__Group__1"
    // InternalAvalla.g:823:1: rule__Invariant__Group__1 : rule__Invariant__Group__1__Impl rule__Invariant__Group__2 ;
    public final void rule__Invariant__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:827:1: ( rule__Invariant__Group__1__Impl rule__Invariant__Group__2 )
            // InternalAvalla.g:828:2: rule__Invariant__Group__1__Impl rule__Invariant__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__Invariant__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Invariant__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__1"


    // $ANTLR start "rule__Invariant__Group__1__Impl"
    // InternalAvalla.g:835:1: rule__Invariant__Group__1__Impl : ( ( rule__Invariant__NameAssignment_1 ) ) ;
    public final void rule__Invariant__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:839:1: ( ( ( rule__Invariant__NameAssignment_1 ) ) )
            // InternalAvalla.g:840:1: ( ( rule__Invariant__NameAssignment_1 ) )
            {
            // InternalAvalla.g:840:1: ( ( rule__Invariant__NameAssignment_1 ) )
            // InternalAvalla.g:841:2: ( rule__Invariant__NameAssignment_1 )
            {
             before(grammarAccess.getInvariantAccess().getNameAssignment_1()); 
            // InternalAvalla.g:842:2: ( rule__Invariant__NameAssignment_1 )
            // InternalAvalla.g:842:3: rule__Invariant__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Invariant__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getInvariantAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__1__Impl"


    // $ANTLR start "rule__Invariant__Group__2"
    // InternalAvalla.g:850:1: rule__Invariant__Group__2 : rule__Invariant__Group__2__Impl rule__Invariant__Group__3 ;
    public final void rule__Invariant__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:854:1: ( rule__Invariant__Group__2__Impl rule__Invariant__Group__3 )
            // InternalAvalla.g:855:2: rule__Invariant__Group__2__Impl rule__Invariant__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__Invariant__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Invariant__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__2"


    // $ANTLR start "rule__Invariant__Group__2__Impl"
    // InternalAvalla.g:862:1: rule__Invariant__Group__2__Impl : ( ':' ) ;
    public final void rule__Invariant__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:866:1: ( ( ':' ) )
            // InternalAvalla.g:867:1: ( ':' )
            {
            // InternalAvalla.g:867:1: ( ':' )
            // InternalAvalla.g:868:2: ':'
            {
             before(grammarAccess.getInvariantAccess().getColonKeyword_2()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getInvariantAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__2__Impl"


    // $ANTLR start "rule__Invariant__Group__3"
    // InternalAvalla.g:877:1: rule__Invariant__Group__3 : rule__Invariant__Group__3__Impl rule__Invariant__Group__4 ;
    public final void rule__Invariant__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:881:1: ( rule__Invariant__Group__3__Impl rule__Invariant__Group__4 )
            // InternalAvalla.g:882:2: rule__Invariant__Group__3__Impl rule__Invariant__Group__4
            {
            pushFollow(FOLLOW_14);
            rule__Invariant__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Invariant__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__3"


    // $ANTLR start "rule__Invariant__Group__3__Impl"
    // InternalAvalla.g:889:1: rule__Invariant__Group__3__Impl : ( ( rule__Invariant__ExpressionAssignment_3 ) ) ;
    public final void rule__Invariant__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:893:1: ( ( ( rule__Invariant__ExpressionAssignment_3 ) ) )
            // InternalAvalla.g:894:1: ( ( rule__Invariant__ExpressionAssignment_3 ) )
            {
            // InternalAvalla.g:894:1: ( ( rule__Invariant__ExpressionAssignment_3 ) )
            // InternalAvalla.g:895:2: ( rule__Invariant__ExpressionAssignment_3 )
            {
             before(grammarAccess.getInvariantAccess().getExpressionAssignment_3()); 
            // InternalAvalla.g:896:2: ( rule__Invariant__ExpressionAssignment_3 )
            // InternalAvalla.g:896:3: rule__Invariant__ExpressionAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Invariant__ExpressionAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getInvariantAccess().getExpressionAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__3__Impl"


    // $ANTLR start "rule__Invariant__Group__4"
    // InternalAvalla.g:904:1: rule__Invariant__Group__4 : rule__Invariant__Group__4__Impl ;
    public final void rule__Invariant__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:908:1: ( rule__Invariant__Group__4__Impl )
            // InternalAvalla.g:909:2: rule__Invariant__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Invariant__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__4"


    // $ANTLR start "rule__Invariant__Group__4__Impl"
    // InternalAvalla.g:915:1: rule__Invariant__Group__4__Impl : ( ';' ) ;
    public final void rule__Invariant__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:919:1: ( ( ';' ) )
            // InternalAvalla.g:920:1: ( ';' )
            {
            // InternalAvalla.g:920:1: ( ';' )
            // InternalAvalla.g:921:2: ';'
            {
             before(grammarAccess.getInvariantAccess().getSemicolonKeyword_4()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getInvariantAccess().getSemicolonKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__Group__4__Impl"


    // $ANTLR start "rule__Command__Group_2__0"
    // InternalAvalla.g:931:1: rule__Command__Group_2__0 : rule__Command__Group_2__0__Impl rule__Command__Group_2__1 ;
    public final void rule__Command__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:935:1: ( rule__Command__Group_2__0__Impl rule__Command__Group_2__1 )
            // InternalAvalla.g:936:2: rule__Command__Group_2__0__Impl rule__Command__Group_2__1
            {
            pushFollow(FOLLOW_15);
            rule__Command__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Command__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Command__Group_2__0"


    // $ANTLR start "rule__Command__Group_2__0__Impl"
    // InternalAvalla.g:943:1: rule__Command__Group_2__0__Impl : ( () ) ;
    public final void rule__Command__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:947:1: ( ( () ) )
            // InternalAvalla.g:948:1: ( () )
            {
            // InternalAvalla.g:948:1: ( () )
            // InternalAvalla.g:949:2: ()
            {
             before(grammarAccess.getCommandAccess().getStepAction_2_0()); 
            // InternalAvalla.g:950:2: ()
            // InternalAvalla.g:950:3: 
            {
            }

             after(grammarAccess.getCommandAccess().getStepAction_2_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Command__Group_2__0__Impl"


    // $ANTLR start "rule__Command__Group_2__1"
    // InternalAvalla.g:958:1: rule__Command__Group_2__1 : rule__Command__Group_2__1__Impl ;
    public final void rule__Command__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:962:1: ( rule__Command__Group_2__1__Impl )
            // InternalAvalla.g:963:2: rule__Command__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Command__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Command__Group_2__1"


    // $ANTLR start "rule__Command__Group_2__1__Impl"
    // InternalAvalla.g:969:1: rule__Command__Group_2__1__Impl : ( ruleStep ) ;
    public final void rule__Command__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:973:1: ( ( ruleStep ) )
            // InternalAvalla.g:974:1: ( ruleStep )
            {
            // InternalAvalla.g:974:1: ( ruleStep )
            // InternalAvalla.g:975:2: ruleStep
            {
             before(grammarAccess.getCommandAccess().getStepParserRuleCall_2_1()); 
            pushFollow(FOLLOW_2);
            ruleStep();

            state._fsp--;

             after(grammarAccess.getCommandAccess().getStepParserRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Command__Group_2__1__Impl"


    // $ANTLR start "rule__Check__Group__0"
    // InternalAvalla.g:985:1: rule__Check__Group__0 : rule__Check__Group__0__Impl rule__Check__Group__1 ;
    public final void rule__Check__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:989:1: ( rule__Check__Group__0__Impl rule__Check__Group__1 )
            // InternalAvalla.g:990:2: rule__Check__Group__0__Impl rule__Check__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__Check__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Check__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__0"


    // $ANTLR start "rule__Check__Group__0__Impl"
    // InternalAvalla.g:997:1: rule__Check__Group__0__Impl : ( 'check' ) ;
    public final void rule__Check__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1001:1: ( ( 'check' ) )
            // InternalAvalla.g:1002:1: ( 'check' )
            {
            // InternalAvalla.g:1002:1: ( 'check' )
            // InternalAvalla.g:1003:2: 'check'
            {
             before(grammarAccess.getCheckAccess().getCheckKeyword_0()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getCheckAccess().getCheckKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__0__Impl"


    // $ANTLR start "rule__Check__Group__1"
    // InternalAvalla.g:1012:1: rule__Check__Group__1 : rule__Check__Group__1__Impl rule__Check__Group__2 ;
    public final void rule__Check__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1016:1: ( rule__Check__Group__1__Impl rule__Check__Group__2 )
            // InternalAvalla.g:1017:2: rule__Check__Group__1__Impl rule__Check__Group__2
            {
            pushFollow(FOLLOW_14);
            rule__Check__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Check__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__1"


    // $ANTLR start "rule__Check__Group__1__Impl"
    // InternalAvalla.g:1024:1: rule__Check__Group__1__Impl : ( ( rule__Check__ExpressionAssignment_1 ) ) ;
    public final void rule__Check__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1028:1: ( ( ( rule__Check__ExpressionAssignment_1 ) ) )
            // InternalAvalla.g:1029:1: ( ( rule__Check__ExpressionAssignment_1 ) )
            {
            // InternalAvalla.g:1029:1: ( ( rule__Check__ExpressionAssignment_1 ) )
            // InternalAvalla.g:1030:2: ( rule__Check__ExpressionAssignment_1 )
            {
             before(grammarAccess.getCheckAccess().getExpressionAssignment_1()); 
            // InternalAvalla.g:1031:2: ( rule__Check__ExpressionAssignment_1 )
            // InternalAvalla.g:1031:3: rule__Check__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Check__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getCheckAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__1__Impl"


    // $ANTLR start "rule__Check__Group__2"
    // InternalAvalla.g:1039:1: rule__Check__Group__2 : rule__Check__Group__2__Impl ;
    public final void rule__Check__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1043:1: ( rule__Check__Group__2__Impl )
            // InternalAvalla.g:1044:2: rule__Check__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Check__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__2"


    // $ANTLR start "rule__Check__Group__2__Impl"
    // InternalAvalla.g:1050:1: rule__Check__Group__2__Impl : ( ';' ) ;
    public final void rule__Check__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1054:1: ( ( ';' ) )
            // InternalAvalla.g:1055:1: ( ';' )
            {
            // InternalAvalla.g:1055:1: ( ';' )
            // InternalAvalla.g:1056:2: ';'
            {
             before(grammarAccess.getCheckAccess().getSemicolonKeyword_2()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getCheckAccess().getSemicolonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__Group__2__Impl"


    // $ANTLR start "rule__Set__Group__0"
    // InternalAvalla.g:1066:1: rule__Set__Group__0 : rule__Set__Group__0__Impl rule__Set__Group__1 ;
    public final void rule__Set__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1070:1: ( rule__Set__Group__0__Impl rule__Set__Group__1 )
            // InternalAvalla.g:1071:2: rule__Set__Group__0__Impl rule__Set__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__Set__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Set__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__0"


    // $ANTLR start "rule__Set__Group__0__Impl"
    // InternalAvalla.g:1078:1: rule__Set__Group__0__Impl : ( 'set' ) ;
    public final void rule__Set__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1082:1: ( ( 'set' ) )
            // InternalAvalla.g:1083:1: ( 'set' )
            {
            // InternalAvalla.g:1083:1: ( 'set' )
            // InternalAvalla.g:1084:2: 'set'
            {
             before(grammarAccess.getSetAccess().getSetKeyword_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getSetAccess().getSetKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__0__Impl"


    // $ANTLR start "rule__Set__Group__1"
    // InternalAvalla.g:1093:1: rule__Set__Group__1 : rule__Set__Group__1__Impl rule__Set__Group__2 ;
    public final void rule__Set__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1097:1: ( rule__Set__Group__1__Impl rule__Set__Group__2 )
            // InternalAvalla.g:1098:2: rule__Set__Group__1__Impl rule__Set__Group__2
            {
            pushFollow(FOLLOW_16);
            rule__Set__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Set__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__1"


    // $ANTLR start "rule__Set__Group__1__Impl"
    // InternalAvalla.g:1105:1: rule__Set__Group__1__Impl : ( ( rule__Set__LocationAssignment_1 ) ) ;
    public final void rule__Set__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1109:1: ( ( ( rule__Set__LocationAssignment_1 ) ) )
            // InternalAvalla.g:1110:1: ( ( rule__Set__LocationAssignment_1 ) )
            {
            // InternalAvalla.g:1110:1: ( ( rule__Set__LocationAssignment_1 ) )
            // InternalAvalla.g:1111:2: ( rule__Set__LocationAssignment_1 )
            {
             before(grammarAccess.getSetAccess().getLocationAssignment_1()); 
            // InternalAvalla.g:1112:2: ( rule__Set__LocationAssignment_1 )
            // InternalAvalla.g:1112:3: rule__Set__LocationAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Set__LocationAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSetAccess().getLocationAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__1__Impl"


    // $ANTLR start "rule__Set__Group__2"
    // InternalAvalla.g:1120:1: rule__Set__Group__2 : rule__Set__Group__2__Impl rule__Set__Group__3 ;
    public final void rule__Set__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1124:1: ( rule__Set__Group__2__Impl rule__Set__Group__3 )
            // InternalAvalla.g:1125:2: rule__Set__Group__2__Impl rule__Set__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__Set__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Set__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__2"


    // $ANTLR start "rule__Set__Group__2__Impl"
    // InternalAvalla.g:1132:1: rule__Set__Group__2__Impl : ( ':=' ) ;
    public final void rule__Set__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1136:1: ( ( ':=' ) )
            // InternalAvalla.g:1137:1: ( ':=' )
            {
            // InternalAvalla.g:1137:1: ( ':=' )
            // InternalAvalla.g:1138:2: ':='
            {
             before(grammarAccess.getSetAccess().getColonEqualsSignKeyword_2()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getSetAccess().getColonEqualsSignKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__2__Impl"


    // $ANTLR start "rule__Set__Group__3"
    // InternalAvalla.g:1147:1: rule__Set__Group__3 : rule__Set__Group__3__Impl rule__Set__Group__4 ;
    public final void rule__Set__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1151:1: ( rule__Set__Group__3__Impl rule__Set__Group__4 )
            // InternalAvalla.g:1152:2: rule__Set__Group__3__Impl rule__Set__Group__4
            {
            pushFollow(FOLLOW_14);
            rule__Set__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Set__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__3"


    // $ANTLR start "rule__Set__Group__3__Impl"
    // InternalAvalla.g:1159:1: rule__Set__Group__3__Impl : ( ( rule__Set__ValueAssignment_3 ) ) ;
    public final void rule__Set__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1163:1: ( ( ( rule__Set__ValueAssignment_3 ) ) )
            // InternalAvalla.g:1164:1: ( ( rule__Set__ValueAssignment_3 ) )
            {
            // InternalAvalla.g:1164:1: ( ( rule__Set__ValueAssignment_3 ) )
            // InternalAvalla.g:1165:2: ( rule__Set__ValueAssignment_3 )
            {
             before(grammarAccess.getSetAccess().getValueAssignment_3()); 
            // InternalAvalla.g:1166:2: ( rule__Set__ValueAssignment_3 )
            // InternalAvalla.g:1166:3: rule__Set__ValueAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Set__ValueAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getSetAccess().getValueAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__3__Impl"


    // $ANTLR start "rule__Set__Group__4"
    // InternalAvalla.g:1174:1: rule__Set__Group__4 : rule__Set__Group__4__Impl ;
    public final void rule__Set__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1178:1: ( rule__Set__Group__4__Impl )
            // InternalAvalla.g:1179:2: rule__Set__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Set__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__4"


    // $ANTLR start "rule__Set__Group__4__Impl"
    // InternalAvalla.g:1185:1: rule__Set__Group__4__Impl : ( ';' ) ;
    public final void rule__Set__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1189:1: ( ( ';' ) )
            // InternalAvalla.g:1190:1: ( ';' )
            {
            // InternalAvalla.g:1190:1: ( ';' )
            // InternalAvalla.g:1191:2: ';'
            {
             before(grammarAccess.getSetAccess().getSemicolonKeyword_4()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getSetAccess().getSemicolonKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__Group__4__Impl"


    // $ANTLR start "rule__StepUntil__Group__0"
    // InternalAvalla.g:1201:1: rule__StepUntil__Group__0 : rule__StepUntil__Group__0__Impl rule__StepUntil__Group__1 ;
    public final void rule__StepUntil__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1205:1: ( rule__StepUntil__Group__0__Impl rule__StepUntil__Group__1 )
            // InternalAvalla.g:1206:2: rule__StepUntil__Group__0__Impl rule__StepUntil__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__StepUntil__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StepUntil__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__0"


    // $ANTLR start "rule__StepUntil__Group__0__Impl"
    // InternalAvalla.g:1213:1: rule__StepUntil__Group__0__Impl : ( 'step' ) ;
    public final void rule__StepUntil__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1217:1: ( ( 'step' ) )
            // InternalAvalla.g:1218:1: ( 'step' )
            {
            // InternalAvalla.g:1218:1: ( 'step' )
            // InternalAvalla.g:1219:2: 'step'
            {
             before(grammarAccess.getStepUntilAccess().getStepKeyword_0()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getStepUntilAccess().getStepKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__0__Impl"


    // $ANTLR start "rule__StepUntil__Group__1"
    // InternalAvalla.g:1228:1: rule__StepUntil__Group__1 : rule__StepUntil__Group__1__Impl rule__StepUntil__Group__2 ;
    public final void rule__StepUntil__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1232:1: ( rule__StepUntil__Group__1__Impl rule__StepUntil__Group__2 )
            // InternalAvalla.g:1233:2: rule__StepUntil__Group__1__Impl rule__StepUntil__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__StepUntil__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StepUntil__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__1"


    // $ANTLR start "rule__StepUntil__Group__1__Impl"
    // InternalAvalla.g:1240:1: rule__StepUntil__Group__1__Impl : ( 'until' ) ;
    public final void rule__StepUntil__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1244:1: ( ( 'until' ) )
            // InternalAvalla.g:1245:1: ( 'until' )
            {
            // InternalAvalla.g:1245:1: ( 'until' )
            // InternalAvalla.g:1246:2: 'until'
            {
             before(grammarAccess.getStepUntilAccess().getUntilKeyword_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getStepUntilAccess().getUntilKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__1__Impl"


    // $ANTLR start "rule__StepUntil__Group__2"
    // InternalAvalla.g:1255:1: rule__StepUntil__Group__2 : rule__StepUntil__Group__2__Impl rule__StepUntil__Group__3 ;
    public final void rule__StepUntil__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1259:1: ( rule__StepUntil__Group__2__Impl rule__StepUntil__Group__3 )
            // InternalAvalla.g:1260:2: rule__StepUntil__Group__2__Impl rule__StepUntil__Group__3
            {
            pushFollow(FOLLOW_14);
            rule__StepUntil__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StepUntil__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__2"


    // $ANTLR start "rule__StepUntil__Group__2__Impl"
    // InternalAvalla.g:1267:1: rule__StepUntil__Group__2__Impl : ( ( rule__StepUntil__ExpressionAssignment_2 ) ) ;
    public final void rule__StepUntil__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1271:1: ( ( ( rule__StepUntil__ExpressionAssignment_2 ) ) )
            // InternalAvalla.g:1272:1: ( ( rule__StepUntil__ExpressionAssignment_2 ) )
            {
            // InternalAvalla.g:1272:1: ( ( rule__StepUntil__ExpressionAssignment_2 ) )
            // InternalAvalla.g:1273:2: ( rule__StepUntil__ExpressionAssignment_2 )
            {
             before(grammarAccess.getStepUntilAccess().getExpressionAssignment_2()); 
            // InternalAvalla.g:1274:2: ( rule__StepUntil__ExpressionAssignment_2 )
            // InternalAvalla.g:1274:3: rule__StepUntil__ExpressionAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__StepUntil__ExpressionAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getStepUntilAccess().getExpressionAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__2__Impl"


    // $ANTLR start "rule__StepUntil__Group__3"
    // InternalAvalla.g:1282:1: rule__StepUntil__Group__3 : rule__StepUntil__Group__3__Impl ;
    public final void rule__StepUntil__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1286:1: ( rule__StepUntil__Group__3__Impl )
            // InternalAvalla.g:1287:2: rule__StepUntil__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StepUntil__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__3"


    // $ANTLR start "rule__StepUntil__Group__3__Impl"
    // InternalAvalla.g:1293:1: rule__StepUntil__Group__3__Impl : ( ';' ) ;
    public final void rule__StepUntil__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1297:1: ( ( ';' ) )
            // InternalAvalla.g:1298:1: ( ';' )
            {
            // InternalAvalla.g:1298:1: ( ';' )
            // InternalAvalla.g:1299:2: ';'
            {
             before(grammarAccess.getStepUntilAccess().getSemicolonKeyword_3()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getStepUntilAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__Group__3__Impl"


    // $ANTLR start "rule__Exec__Group__0"
    // InternalAvalla.g:1309:1: rule__Exec__Group__0 : rule__Exec__Group__0__Impl rule__Exec__Group__1 ;
    public final void rule__Exec__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1313:1: ( rule__Exec__Group__0__Impl rule__Exec__Group__1 )
            // InternalAvalla.g:1314:2: rule__Exec__Group__0__Impl rule__Exec__Group__1
            {
            pushFollow(FOLLOW_18);
            rule__Exec__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Exec__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__0"


    // $ANTLR start "rule__Exec__Group__0__Impl"
    // InternalAvalla.g:1321:1: rule__Exec__Group__0__Impl : ( 'exec' ) ;
    public final void rule__Exec__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1325:1: ( ( 'exec' ) )
            // InternalAvalla.g:1326:1: ( 'exec' )
            {
            // InternalAvalla.g:1326:1: ( 'exec' )
            // InternalAvalla.g:1327:2: 'exec'
            {
             before(grammarAccess.getExecAccess().getExecKeyword_0()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getExecAccess().getExecKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__0__Impl"


    // $ANTLR start "rule__Exec__Group__1"
    // InternalAvalla.g:1336:1: rule__Exec__Group__1 : rule__Exec__Group__1__Impl rule__Exec__Group__2 ;
    public final void rule__Exec__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1340:1: ( rule__Exec__Group__1__Impl rule__Exec__Group__2 )
            // InternalAvalla.g:1341:2: rule__Exec__Group__1__Impl rule__Exec__Group__2
            {
            pushFollow(FOLLOW_14);
            rule__Exec__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Exec__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__1"


    // $ANTLR start "rule__Exec__Group__1__Impl"
    // InternalAvalla.g:1348:1: rule__Exec__Group__1__Impl : ( ( rule__Exec__RuleAssignment_1 ) ) ;
    public final void rule__Exec__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1352:1: ( ( ( rule__Exec__RuleAssignment_1 ) ) )
            // InternalAvalla.g:1353:1: ( ( rule__Exec__RuleAssignment_1 ) )
            {
            // InternalAvalla.g:1353:1: ( ( rule__Exec__RuleAssignment_1 ) )
            // InternalAvalla.g:1354:2: ( rule__Exec__RuleAssignment_1 )
            {
             before(grammarAccess.getExecAccess().getRuleAssignment_1()); 
            // InternalAvalla.g:1355:2: ( rule__Exec__RuleAssignment_1 )
            // InternalAvalla.g:1355:3: rule__Exec__RuleAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Exec__RuleAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getExecAccess().getRuleAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__1__Impl"


    // $ANTLR start "rule__Exec__Group__2"
    // InternalAvalla.g:1363:1: rule__Exec__Group__2 : rule__Exec__Group__2__Impl ;
    public final void rule__Exec__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1367:1: ( rule__Exec__Group__2__Impl )
            // InternalAvalla.g:1368:2: rule__Exec__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Exec__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__2"


    // $ANTLR start "rule__Exec__Group__2__Impl"
    // InternalAvalla.g:1374:1: rule__Exec__Group__2__Impl : ( ';' ) ;
    public final void rule__Exec__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1378:1: ( ( ';' ) )
            // InternalAvalla.g:1379:1: ( ';' )
            {
            // InternalAvalla.g:1379:1: ( ';' )
            // InternalAvalla.g:1380:2: ';'
            {
             before(grammarAccess.getExecAccess().getSemicolonKeyword_2()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getExecAccess().getSemicolonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__Group__2__Impl"


    // $ANTLR start "rule__Choose__Group__0"
    // InternalAvalla.g:1390:1: rule__Choose__Group__0 : rule__Choose__Group__0__Impl rule__Choose__Group__1 ;
    public final void rule__Choose__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1394:1: ( rule__Choose__Group__0__Impl rule__Choose__Group__1 )
            // InternalAvalla.g:1395:2: rule__Choose__Group__0__Impl rule__Choose__Group__1
            {
            pushFollow(FOLLOW_19);
            rule__Choose__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Choose__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__0"


    // $ANTLR start "rule__Choose__Group__0__Impl"
    // InternalAvalla.g:1402:1: rule__Choose__Group__0__Impl : ( 'setchoose' ) ;
    public final void rule__Choose__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1406:1: ( ( 'setchoose' ) )
            // InternalAvalla.g:1407:1: ( 'setchoose' )
            {
            // InternalAvalla.g:1407:1: ( 'setchoose' )
            // InternalAvalla.g:1408:2: 'setchoose'
            {
             before(grammarAccess.getChooseAccess().getSetchooseKeyword_0()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getChooseAccess().getSetchooseKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__0__Impl"


    // $ANTLR start "rule__Choose__Group__1"
    // InternalAvalla.g:1417:1: rule__Choose__Group__1 : rule__Choose__Group__1__Impl rule__Choose__Group__2 ;
    public final void rule__Choose__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1421:1: ( rule__Choose__Group__1__Impl rule__Choose__Group__2 )
            // InternalAvalla.g:1422:2: rule__Choose__Group__1__Impl rule__Choose__Group__2
            {
            pushFollow(FOLLOW_16);
            rule__Choose__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Choose__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__1"


    // $ANTLR start "rule__Choose__Group__1__Impl"
    // InternalAvalla.g:1429:1: rule__Choose__Group__1__Impl : ( ( rule__Choose__VarAssignment_1 ) ) ;
    public final void rule__Choose__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1433:1: ( ( ( rule__Choose__VarAssignment_1 ) ) )
            // InternalAvalla.g:1434:1: ( ( rule__Choose__VarAssignment_1 ) )
            {
            // InternalAvalla.g:1434:1: ( ( rule__Choose__VarAssignment_1 ) )
            // InternalAvalla.g:1435:2: ( rule__Choose__VarAssignment_1 )
            {
             before(grammarAccess.getChooseAccess().getVarAssignment_1()); 
            // InternalAvalla.g:1436:2: ( rule__Choose__VarAssignment_1 )
            // InternalAvalla.g:1436:3: rule__Choose__VarAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Choose__VarAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getChooseAccess().getVarAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__1__Impl"


    // $ANTLR start "rule__Choose__Group__2"
    // InternalAvalla.g:1444:1: rule__Choose__Group__2 : rule__Choose__Group__2__Impl rule__Choose__Group__3 ;
    public final void rule__Choose__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1448:1: ( rule__Choose__Group__2__Impl rule__Choose__Group__3 )
            // InternalAvalla.g:1449:2: rule__Choose__Group__2__Impl rule__Choose__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__Choose__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Choose__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__2"


    // $ANTLR start "rule__Choose__Group__2__Impl"
    // InternalAvalla.g:1456:1: rule__Choose__Group__2__Impl : ( ':=' ) ;
    public final void rule__Choose__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1460:1: ( ( ':=' ) )
            // InternalAvalla.g:1461:1: ( ':=' )
            {
            // InternalAvalla.g:1461:1: ( ':=' )
            // InternalAvalla.g:1462:2: ':='
            {
             before(grammarAccess.getChooseAccess().getColonEqualsSignKeyword_2()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getChooseAccess().getColonEqualsSignKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__2__Impl"


    // $ANTLR start "rule__Choose__Group__3"
    // InternalAvalla.g:1471:1: rule__Choose__Group__3 : rule__Choose__Group__3__Impl rule__Choose__Group__4 ;
    public final void rule__Choose__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1475:1: ( rule__Choose__Group__3__Impl rule__Choose__Group__4 )
            // InternalAvalla.g:1476:2: rule__Choose__Group__3__Impl rule__Choose__Group__4
            {
            pushFollow(FOLLOW_14);
            rule__Choose__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Choose__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__3"


    // $ANTLR start "rule__Choose__Group__3__Impl"
    // InternalAvalla.g:1483:1: rule__Choose__Group__3__Impl : ( ( rule__Choose__ValueAssignment_3 ) ) ;
    public final void rule__Choose__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1487:1: ( ( ( rule__Choose__ValueAssignment_3 ) ) )
            // InternalAvalla.g:1488:1: ( ( rule__Choose__ValueAssignment_3 ) )
            {
            // InternalAvalla.g:1488:1: ( ( rule__Choose__ValueAssignment_3 ) )
            // InternalAvalla.g:1489:2: ( rule__Choose__ValueAssignment_3 )
            {
             before(grammarAccess.getChooseAccess().getValueAssignment_3()); 
            // InternalAvalla.g:1490:2: ( rule__Choose__ValueAssignment_3 )
            // InternalAvalla.g:1490:3: rule__Choose__ValueAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Choose__ValueAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getChooseAccess().getValueAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__3__Impl"


    // $ANTLR start "rule__Choose__Group__4"
    // InternalAvalla.g:1498:1: rule__Choose__Group__4 : rule__Choose__Group__4__Impl ;
    public final void rule__Choose__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1502:1: ( rule__Choose__Group__4__Impl )
            // InternalAvalla.g:1503:2: rule__Choose__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Choose__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__4"


    // $ANTLR start "rule__Choose__Group__4__Impl"
    // InternalAvalla.g:1509:1: rule__Choose__Group__4__Impl : ( ';' ) ;
    public final void rule__Choose__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1513:1: ( ( ';' ) )
            // InternalAvalla.g:1514:1: ( ';' )
            {
            // InternalAvalla.g:1514:1: ( ';' )
            // InternalAvalla.g:1515:2: ';'
            {
             before(grammarAccess.getChooseAccess().getSemicolonKeyword_4()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getChooseAccess().getSemicolonKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__Group__4__Impl"


    // $ANTLR start "rule__Block__Group__0"
    // InternalAvalla.g:1525:1: rule__Block__Group__0 : rule__Block__Group__0__Impl rule__Block__Group__1 ;
    public final void rule__Block__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1529:1: ( rule__Block__Group__0__Impl rule__Block__Group__1 )
            // InternalAvalla.g:1530:2: rule__Block__Group__0__Impl rule__Block__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Block__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Block__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__0"


    // $ANTLR start "rule__Block__Group__0__Impl"
    // InternalAvalla.g:1537:1: rule__Block__Group__0__Impl : ( 'begin' ) ;
    public final void rule__Block__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1541:1: ( ( 'begin' ) )
            // InternalAvalla.g:1542:1: ( 'begin' )
            {
            // InternalAvalla.g:1542:1: ( 'begin' )
            // InternalAvalla.g:1543:2: 'begin'
            {
             before(grammarAccess.getBlockAccess().getBeginKeyword_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getBlockAccess().getBeginKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__0__Impl"


    // $ANTLR start "rule__Block__Group__1"
    // InternalAvalla.g:1552:1: rule__Block__Group__1 : rule__Block__Group__1__Impl rule__Block__Group__2 ;
    public final void rule__Block__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1556:1: ( rule__Block__Group__1__Impl rule__Block__Group__2 )
            // InternalAvalla.g:1557:2: rule__Block__Group__1__Impl rule__Block__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__Block__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Block__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__1"


    // $ANTLR start "rule__Block__Group__1__Impl"
    // InternalAvalla.g:1564:1: rule__Block__Group__1__Impl : ( ( rule__Block__NameAssignment_1 ) ) ;
    public final void rule__Block__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1568:1: ( ( ( rule__Block__NameAssignment_1 ) ) )
            // InternalAvalla.g:1569:1: ( ( rule__Block__NameAssignment_1 ) )
            {
            // InternalAvalla.g:1569:1: ( ( rule__Block__NameAssignment_1 ) )
            // InternalAvalla.g:1570:2: ( rule__Block__NameAssignment_1 )
            {
             before(grammarAccess.getBlockAccess().getNameAssignment_1()); 
            // InternalAvalla.g:1571:2: ( rule__Block__NameAssignment_1 )
            // InternalAvalla.g:1571:3: rule__Block__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Block__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getBlockAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__1__Impl"


    // $ANTLR start "rule__Block__Group__2"
    // InternalAvalla.g:1579:1: rule__Block__Group__2 : rule__Block__Group__2__Impl rule__Block__Group__3 ;
    public final void rule__Block__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1583:1: ( rule__Block__Group__2__Impl rule__Block__Group__3 )
            // InternalAvalla.g:1584:2: rule__Block__Group__2__Impl rule__Block__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__Block__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Block__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__2"


    // $ANTLR start "rule__Block__Group__2__Impl"
    // InternalAvalla.g:1591:1: rule__Block__Group__2__Impl : ( ( rule__Block__ElementsAssignment_2 )* ) ;
    public final void rule__Block__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1595:1: ( ( ( rule__Block__ElementsAssignment_2 )* ) )
            // InternalAvalla.g:1596:1: ( ( rule__Block__ElementsAssignment_2 )* )
            {
            // InternalAvalla.g:1596:1: ( ( rule__Block__ElementsAssignment_2 )* )
            // InternalAvalla.g:1597:2: ( rule__Block__ElementsAssignment_2 )*
            {
             before(grammarAccess.getBlockAccess().getElementsAssignment_2()); 
            // InternalAvalla.g:1598:2: ( rule__Block__ElementsAssignment_2 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==11||(LA12_0>=18 && LA12_0<=19)||(LA12_0>=21 && LA12_0<=23)||LA12_0==25) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalAvalla.g:1598:3: rule__Block__ElementsAssignment_2
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__Block__ElementsAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getBlockAccess().getElementsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__2__Impl"


    // $ANTLR start "rule__Block__Group__3"
    // InternalAvalla.g:1606:1: rule__Block__Group__3 : rule__Block__Group__3__Impl ;
    public final void rule__Block__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1610:1: ( rule__Block__Group__3__Impl )
            // InternalAvalla.g:1611:2: rule__Block__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Block__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__3"


    // $ANTLR start "rule__Block__Group__3__Impl"
    // InternalAvalla.g:1617:1: rule__Block__Group__3__Impl : ( 'end' ) ;
    public final void rule__Block__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1621:1: ( ( 'end' ) )
            // InternalAvalla.g:1622:1: ( 'end' )
            {
            // InternalAvalla.g:1622:1: ( 'end' )
            // InternalAvalla.g:1623:2: 'end'
            {
             before(grammarAccess.getBlockAccess().getEndKeyword_3()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getBlockAccess().getEndKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__Group__3__Impl"


    // $ANTLR start "rule__ExecBlock__Group__0"
    // InternalAvalla.g:1633:1: rule__ExecBlock__Group__0 : rule__ExecBlock__Group__0__Impl rule__ExecBlock__Group__1 ;
    public final void rule__ExecBlock__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1637:1: ( rule__ExecBlock__Group__0__Impl rule__ExecBlock__Group__1 )
            // InternalAvalla.g:1638:2: rule__ExecBlock__Group__0__Impl rule__ExecBlock__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__ExecBlock__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__0"


    // $ANTLR start "rule__ExecBlock__Group__0__Impl"
    // InternalAvalla.g:1645:1: rule__ExecBlock__Group__0__Impl : ( 'execblock' ) ;
    public final void rule__ExecBlock__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1649:1: ( ( 'execblock' ) )
            // InternalAvalla.g:1650:1: ( 'execblock' )
            {
            // InternalAvalla.g:1650:1: ( 'execblock' )
            // InternalAvalla.g:1651:2: 'execblock'
            {
             before(grammarAccess.getExecBlockAccess().getExecblockKeyword_0()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getExecBlockAccess().getExecblockKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__0__Impl"


    // $ANTLR start "rule__ExecBlock__Group__1"
    // InternalAvalla.g:1660:1: rule__ExecBlock__Group__1 : rule__ExecBlock__Group__1__Impl rule__ExecBlock__Group__2 ;
    public final void rule__ExecBlock__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1664:1: ( rule__ExecBlock__Group__1__Impl rule__ExecBlock__Group__2 )
            // InternalAvalla.g:1665:2: rule__ExecBlock__Group__1__Impl rule__ExecBlock__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__ExecBlock__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__1"


    // $ANTLR start "rule__ExecBlock__Group__1__Impl"
    // InternalAvalla.g:1672:1: rule__ExecBlock__Group__1__Impl : ( ( rule__ExecBlock__Group_1__0 )? ) ;
    public final void rule__ExecBlock__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1676:1: ( ( ( rule__ExecBlock__Group_1__0 )? ) )
            // InternalAvalla.g:1677:1: ( ( rule__ExecBlock__Group_1__0 )? )
            {
            // InternalAvalla.g:1677:1: ( ( rule__ExecBlock__Group_1__0 )? )
            // InternalAvalla.g:1678:2: ( rule__ExecBlock__Group_1__0 )?
            {
             before(grammarAccess.getExecBlockAccess().getGroup_1()); 
            // InternalAvalla.g:1679:2: ( rule__ExecBlock__Group_1__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_GOOD_CHARS_NO_COLON) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==12) ) {
                    alt13=1;
                }
            }
            switch (alt13) {
                case 1 :
                    // InternalAvalla.g:1679:3: rule__ExecBlock__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExecBlock__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getExecBlockAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__1__Impl"


    // $ANTLR start "rule__ExecBlock__Group__2"
    // InternalAvalla.g:1687:1: rule__ExecBlock__Group__2 : rule__ExecBlock__Group__2__Impl rule__ExecBlock__Group__3 ;
    public final void rule__ExecBlock__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1691:1: ( rule__ExecBlock__Group__2__Impl rule__ExecBlock__Group__3 )
            // InternalAvalla.g:1692:2: rule__ExecBlock__Group__2__Impl rule__ExecBlock__Group__3
            {
            pushFollow(FOLLOW_14);
            rule__ExecBlock__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__2"


    // $ANTLR start "rule__ExecBlock__Group__2__Impl"
    // InternalAvalla.g:1699:1: rule__ExecBlock__Group__2__Impl : ( ( rule__ExecBlock__BlockAssignment_2 ) ) ;
    public final void rule__ExecBlock__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1703:1: ( ( ( rule__ExecBlock__BlockAssignment_2 ) ) )
            // InternalAvalla.g:1704:1: ( ( rule__ExecBlock__BlockAssignment_2 ) )
            {
            // InternalAvalla.g:1704:1: ( ( rule__ExecBlock__BlockAssignment_2 ) )
            // InternalAvalla.g:1705:2: ( rule__ExecBlock__BlockAssignment_2 )
            {
             before(grammarAccess.getExecBlockAccess().getBlockAssignment_2()); 
            // InternalAvalla.g:1706:2: ( rule__ExecBlock__BlockAssignment_2 )
            // InternalAvalla.g:1706:3: rule__ExecBlock__BlockAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__ExecBlock__BlockAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getExecBlockAccess().getBlockAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__2__Impl"


    // $ANTLR start "rule__ExecBlock__Group__3"
    // InternalAvalla.g:1714:1: rule__ExecBlock__Group__3 : rule__ExecBlock__Group__3__Impl ;
    public final void rule__ExecBlock__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1718:1: ( rule__ExecBlock__Group__3__Impl )
            // InternalAvalla.g:1719:2: rule__ExecBlock__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__3"


    // $ANTLR start "rule__ExecBlock__Group__3__Impl"
    // InternalAvalla.g:1725:1: rule__ExecBlock__Group__3__Impl : ( ';' ) ;
    public final void rule__ExecBlock__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1729:1: ( ( ';' ) )
            // InternalAvalla.g:1730:1: ( ';' )
            {
            // InternalAvalla.g:1730:1: ( ';' )
            // InternalAvalla.g:1731:2: ';'
            {
             before(grammarAccess.getExecBlockAccess().getSemicolonKeyword_3()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getExecBlockAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group__3__Impl"


    // $ANTLR start "rule__ExecBlock__Group_1__0"
    // InternalAvalla.g:1741:1: rule__ExecBlock__Group_1__0 : rule__ExecBlock__Group_1__0__Impl rule__ExecBlock__Group_1__1 ;
    public final void rule__ExecBlock__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1745:1: ( rule__ExecBlock__Group_1__0__Impl rule__ExecBlock__Group_1__1 )
            // InternalAvalla.g:1746:2: rule__ExecBlock__Group_1__0__Impl rule__ExecBlock__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__ExecBlock__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group_1__0"


    // $ANTLR start "rule__ExecBlock__Group_1__0__Impl"
    // InternalAvalla.g:1753:1: rule__ExecBlock__Group_1__0__Impl : ( ( rule__ExecBlock__ScenarioAssignment_1_0 ) ) ;
    public final void rule__ExecBlock__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1757:1: ( ( ( rule__ExecBlock__ScenarioAssignment_1_0 ) ) )
            // InternalAvalla.g:1758:1: ( ( rule__ExecBlock__ScenarioAssignment_1_0 ) )
            {
            // InternalAvalla.g:1758:1: ( ( rule__ExecBlock__ScenarioAssignment_1_0 ) )
            // InternalAvalla.g:1759:2: ( rule__ExecBlock__ScenarioAssignment_1_0 )
            {
             before(grammarAccess.getExecBlockAccess().getScenarioAssignment_1_0()); 
            // InternalAvalla.g:1760:2: ( rule__ExecBlock__ScenarioAssignment_1_0 )
            // InternalAvalla.g:1760:3: rule__ExecBlock__ScenarioAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__ExecBlock__ScenarioAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getExecBlockAccess().getScenarioAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group_1__0__Impl"


    // $ANTLR start "rule__ExecBlock__Group_1__1"
    // InternalAvalla.g:1768:1: rule__ExecBlock__Group_1__1 : rule__ExecBlock__Group_1__1__Impl ;
    public final void rule__ExecBlock__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1772:1: ( rule__ExecBlock__Group_1__1__Impl )
            // InternalAvalla.g:1773:2: rule__ExecBlock__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExecBlock__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group_1__1"


    // $ANTLR start "rule__ExecBlock__Group_1__1__Impl"
    // InternalAvalla.g:1779:1: rule__ExecBlock__Group_1__1__Impl : ( ':' ) ;
    public final void rule__ExecBlock__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1783:1: ( ( ':' ) )
            // InternalAvalla.g:1784:1: ( ':' )
            {
            // InternalAvalla.g:1784:1: ( ':' )
            // InternalAvalla.g:1785:2: ':'
            {
             before(grammarAccess.getExecBlockAccess().getColonKeyword_1_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getExecBlockAccess().getColonKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__Group_1__1__Impl"


    // $ANTLR start "rule__Scenario__NameAssignment_1"
    // InternalAvalla.g:1795:1: rule__Scenario__NameAssignment_1 : ( RULE_GOOD_CHARS_NO_COLON ) ;
    public final void rule__Scenario__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1799:1: ( ( RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:1800:2: ( RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:1800:2: ( RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:1801:3: RULE_GOOD_CHARS_NO_COLON
            {
             before(grammarAccess.getScenarioAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 
            match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
             after(grammarAccess.getScenarioAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__NameAssignment_1"


    // $ANTLR start "rule__Scenario__SpecAssignment_3"
    // InternalAvalla.g:1810:1: rule__Scenario__SpecAssignment_3 : ( rulePath ) ;
    public final void rule__Scenario__SpecAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1814:1: ( ( rulePath ) )
            // InternalAvalla.g:1815:2: ( rulePath )
            {
            // InternalAvalla.g:1815:2: ( rulePath )
            // InternalAvalla.g:1816:3: rulePath
            {
             before(grammarAccess.getScenarioAccess().getSpecPathParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            rulePath();

            state._fsp--;

             after(grammarAccess.getScenarioAccess().getSpecPathParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__SpecAssignment_3"


    // $ANTLR start "rule__Scenario__InvariantsAssignment_4"
    // InternalAvalla.g:1825:1: rule__Scenario__InvariantsAssignment_4 : ( ruleInvariant ) ;
    public final void rule__Scenario__InvariantsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1829:1: ( ( ruleInvariant ) )
            // InternalAvalla.g:1830:2: ( ruleInvariant )
            {
            // InternalAvalla.g:1830:2: ( ruleInvariant )
            // InternalAvalla.g:1831:3: ruleInvariant
            {
             before(grammarAccess.getScenarioAccess().getInvariantsInvariantParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleInvariant();

            state._fsp--;

             after(grammarAccess.getScenarioAccess().getInvariantsInvariantParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__InvariantsAssignment_4"


    // $ANTLR start "rule__Scenario__ElementsAssignment_5"
    // InternalAvalla.g:1840:1: rule__Scenario__ElementsAssignment_5 : ( ruleElement ) ;
    public final void rule__Scenario__ElementsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1844:1: ( ( ruleElement ) )
            // InternalAvalla.g:1845:2: ( ruleElement )
            {
            // InternalAvalla.g:1845:2: ( ruleElement )
            // InternalAvalla.g:1846:3: ruleElement
            {
             before(grammarAccess.getScenarioAccess().getElementsElementParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleElement();

            state._fsp--;

             after(grammarAccess.getScenarioAccess().getElementsElementParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Scenario__ElementsAssignment_5"


    // $ANTLR start "rule__Invariant__NameAssignment_1"
    // InternalAvalla.g:1855:1: rule__Invariant__NameAssignment_1 : ( RULE_GOOD_CHARS_NO_COLON ) ;
    public final void rule__Invariant__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1859:1: ( ( RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:1860:2: ( RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:1860:2: ( RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:1861:3: RULE_GOOD_CHARS_NO_COLON
            {
             before(grammarAccess.getInvariantAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 
            match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
             after(grammarAccess.getInvariantAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__NameAssignment_1"


    // $ANTLR start "rule__Invariant__ExpressionAssignment_3"
    // InternalAvalla.g:1870:1: rule__Invariant__ExpressionAssignment_3 : ( rulesentence ) ;
    public final void rule__Invariant__ExpressionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1874:1: ( ( rulesentence ) )
            // InternalAvalla.g:1875:2: ( rulesentence )
            {
            // InternalAvalla.g:1875:2: ( rulesentence )
            // InternalAvalla.g:1876:3: rulesentence
            {
             before(grammarAccess.getInvariantAccess().getExpressionSentenceParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getInvariantAccess().getExpressionSentenceParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Invariant__ExpressionAssignment_3"


    // $ANTLR start "rule__Check__ExpressionAssignment_1"
    // InternalAvalla.g:1885:1: rule__Check__ExpressionAssignment_1 : ( rulesentence ) ;
    public final void rule__Check__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1889:1: ( ( rulesentence ) )
            // InternalAvalla.g:1890:2: ( rulesentence )
            {
            // InternalAvalla.g:1890:2: ( rulesentence )
            // InternalAvalla.g:1891:3: rulesentence
            {
             before(grammarAccess.getCheckAccess().getExpressionSentenceParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getCheckAccess().getExpressionSentenceParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Check__ExpressionAssignment_1"


    // $ANTLR start "rule__Set__LocationAssignment_1"
    // InternalAvalla.g:1900:1: rule__Set__LocationAssignment_1 : ( rulesentence ) ;
    public final void rule__Set__LocationAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1904:1: ( ( rulesentence ) )
            // InternalAvalla.g:1905:2: ( rulesentence )
            {
            // InternalAvalla.g:1905:2: ( rulesentence )
            // InternalAvalla.g:1906:3: rulesentence
            {
             before(grammarAccess.getSetAccess().getLocationSentenceParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getSetAccess().getLocationSentenceParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__LocationAssignment_1"


    // $ANTLR start "rule__Set__ValueAssignment_3"
    // InternalAvalla.g:1915:1: rule__Set__ValueAssignment_3 : ( rulesentence ) ;
    public final void rule__Set__ValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1919:1: ( ( rulesentence ) )
            // InternalAvalla.g:1920:2: ( rulesentence )
            {
            // InternalAvalla.g:1920:2: ( rulesentence )
            // InternalAvalla.g:1921:3: rulesentence
            {
             before(grammarAccess.getSetAccess().getValueSentenceParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getSetAccess().getValueSentenceParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Set__ValueAssignment_3"


    // $ANTLR start "rule__StepUntil__ExpressionAssignment_2"
    // InternalAvalla.g:1930:1: rule__StepUntil__ExpressionAssignment_2 : ( rulesentence ) ;
    public final void rule__StepUntil__ExpressionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1934:1: ( ( rulesentence ) )
            // InternalAvalla.g:1935:2: ( rulesentence )
            {
            // InternalAvalla.g:1935:2: ( rulesentence )
            // InternalAvalla.g:1936:3: rulesentence
            {
             before(grammarAccess.getStepUntilAccess().getExpressionSentenceParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getStepUntilAccess().getExpressionSentenceParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StepUntil__ExpressionAssignment_2"


    // $ANTLR start "rule__Exec__RuleAssignment_1"
    // InternalAvalla.g:1945:1: rule__Exec__RuleAssignment_1 : ( rulesentencePlusAssignAndColon ) ;
    public final void rule__Exec__RuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1949:1: ( ( rulesentencePlusAssignAndColon ) )
            // InternalAvalla.g:1950:2: ( rulesentencePlusAssignAndColon )
            {
            // InternalAvalla.g:1950:2: ( rulesentencePlusAssignAndColon )
            // InternalAvalla.g:1951:3: rulesentencePlusAssignAndColon
            {
             before(grammarAccess.getExecAccess().getRuleSentencePlusAssignAndColonParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            rulesentencePlusAssignAndColon();

            state._fsp--;

             after(grammarAccess.getExecAccess().getRuleSentencePlusAssignAndColonParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Exec__RuleAssignment_1"


    // $ANTLR start "rule__Choose__VarAssignment_1"
    // InternalAvalla.g:1960:1: rule__Choose__VarAssignment_1 : ( RULE_LOCAL_VARIABLE ) ;
    public final void rule__Choose__VarAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1964:1: ( ( RULE_LOCAL_VARIABLE ) )
            // InternalAvalla.g:1965:2: ( RULE_LOCAL_VARIABLE )
            {
            // InternalAvalla.g:1965:2: ( RULE_LOCAL_VARIABLE )
            // InternalAvalla.g:1966:3: RULE_LOCAL_VARIABLE
            {
             before(grammarAccess.getChooseAccess().getVarLOCAL_VARIABLETerminalRuleCall_1_0()); 
            match(input,RULE_LOCAL_VARIABLE,FOLLOW_2); 
             after(grammarAccess.getChooseAccess().getVarLOCAL_VARIABLETerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__VarAssignment_1"


    // $ANTLR start "rule__Choose__ValueAssignment_3"
    // InternalAvalla.g:1975:1: rule__Choose__ValueAssignment_3 : ( rulesentence ) ;
    public final void rule__Choose__ValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1979:1: ( ( rulesentence ) )
            // InternalAvalla.g:1980:2: ( rulesentence )
            {
            // InternalAvalla.g:1980:2: ( rulesentence )
            // InternalAvalla.g:1981:3: rulesentence
            {
             before(grammarAccess.getChooseAccess().getValueSentenceParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            rulesentence();

            state._fsp--;

             after(grammarAccess.getChooseAccess().getValueSentenceParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Choose__ValueAssignment_3"


    // $ANTLR start "rule__Block__NameAssignment_1"
    // InternalAvalla.g:1990:1: rule__Block__NameAssignment_1 : ( RULE_GOOD_CHARS_NO_COLON ) ;
    public final void rule__Block__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:1994:1: ( ( RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:1995:2: ( RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:1995:2: ( RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:1996:3: RULE_GOOD_CHARS_NO_COLON
            {
             before(grammarAccess.getBlockAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 
            match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
             after(grammarAccess.getBlockAccess().getNameGOOD_CHARS_NO_COLONTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__NameAssignment_1"


    // $ANTLR start "rule__Block__ElementsAssignment_2"
    // InternalAvalla.g:2005:1: rule__Block__ElementsAssignment_2 : ( ruleElement ) ;
    public final void rule__Block__ElementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:2009:1: ( ( ruleElement ) )
            // InternalAvalla.g:2010:2: ( ruleElement )
            {
            // InternalAvalla.g:2010:2: ( ruleElement )
            // InternalAvalla.g:2011:3: ruleElement
            {
             before(grammarAccess.getBlockAccess().getElementsElementParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleElement();

            state._fsp--;

             after(grammarAccess.getBlockAccess().getElementsElementParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Block__ElementsAssignment_2"


    // $ANTLR start "rule__ExecBlock__ScenarioAssignment_1_0"
    // InternalAvalla.g:2020:1: rule__ExecBlock__ScenarioAssignment_1_0 : ( RULE_GOOD_CHARS_NO_COLON ) ;
    public final void rule__ExecBlock__ScenarioAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:2024:1: ( ( RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:2025:2: ( RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:2025:2: ( RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:2026:3: RULE_GOOD_CHARS_NO_COLON
            {
             before(grammarAccess.getExecBlockAccess().getScenarioGOOD_CHARS_NO_COLONTerminalRuleCall_1_0_0()); 
            match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
             after(grammarAccess.getExecBlockAccess().getScenarioGOOD_CHARS_NO_COLONTerminalRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__ScenarioAssignment_1_0"


    // $ANTLR start "rule__ExecBlock__BlockAssignment_2"
    // InternalAvalla.g:2035:1: rule__ExecBlock__BlockAssignment_2 : ( RULE_GOOD_CHARS_NO_COLON ) ;
    public final void rule__ExecBlock__BlockAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalAvalla.g:2039:1: ( ( RULE_GOOD_CHARS_NO_COLON ) )
            // InternalAvalla.g:2040:2: ( RULE_GOOD_CHARS_NO_COLON )
            {
            // InternalAvalla.g:2040:2: ( RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:2041:3: RULE_GOOD_CHARS_NO_COLON
            {
             before(grammarAccess.getExecBlockAccess().getBlockGOOD_CHARS_NO_COLONTerminalRuleCall_2_0()); 
            match(input,RULE_GOOD_CHARS_NO_COLON,FOLLOW_2); 
             after(grammarAccess.getExecBlockAccess().getBlockGOOD_CHARS_NO_COLONTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecBlock__BlockAssignment_2"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001072L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000003072L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000001022L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000001030L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000002ED0800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000002EC0802L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000001070L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000003070L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000003EC0800L});

}