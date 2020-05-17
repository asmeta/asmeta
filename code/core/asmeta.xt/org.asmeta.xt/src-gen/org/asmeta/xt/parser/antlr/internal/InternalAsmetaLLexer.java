package org.asmeta.xt.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAsmetaLLexer extends Lexer {
    public static final int T__144=144;
    public static final int T__143=143;
    public static final int T__50=50;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int T__141=141;
    public static final int RULE_REAL_NUMBER=13;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__137=137;
    public static final int T__52=52;
    public static final int T__136=136;
    public static final int T__53=53;
    public static final int T__139=139;
    public static final int T__54=54;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__60=60;
    public static final int T__135=135;
    public static final int T__61=61;
    public static final int T__134=134;
    public static final int RULE_ID=5;
    public static final int T__131=131;
    public static final int T__130=130;
    public static final int RULE_DIGIT=14;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=21;
    public static final int T__67=67;
    public static final int T__129=129;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int RULE_RULE_ID=6;
    public static final int T__62=62;
    public static final int T__126=126;
    public static final int T__63=63;
    public static final int T__125=125;
    public static final int T__64=64;
    public static final int T__128=128;
    public static final int T__65=65;
    public static final int T__127=127;
    public static final int RULE_SPECIAL_CHAR=18;
    public static final int RULE_ENUM_ID=7;
    public static final int RULE_MIN_ID=16;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_MAIUSC_ID=15;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_NATNUMBER=9;
    public static final int RULE_IMMAGINARY_NUMBER=20;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_NUMBER_TOKEN=12;
    public static final int RULE_ACCENT_CHR=17;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__122=122;
    public static final int T__70=70;
    public static final int T__121=121;
    public static final int T__71=71;
    public static final int T__124=124;
    public static final int T__72=72;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int RULE_STRING=4;
    public static final int RULE_STRING_LITERAL=11;
    public static final int RULE_SL_COMMENT=22;
    public static final int T__77=77;
    public static final int T__119=119;
    public static final int T__78=78;
    public static final int T__118=118;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__117=117;
    public static final int T__76=76;
    public static final int T__116=116;
    public static final int T__80=80;
    public static final int T__111=111;
    public static final int T__81=81;
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int RULE_WS=19;
    public static final int RULE_COMPLEX_NUMBER=8;
    public static final int RULE_CHAR_LITERAL=10;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    // delegators

    public InternalAsmetaLLexer() {;} 
    public InternalAsmetaLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalAsmetaLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalAsmetaL.g"; }

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:11:7: ( 'asyncr' )
            // InternalAsmetaL.g:11:9: 'asyncr'
            {
            match("asyncr"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12:7: ( 'asm' )
            // InternalAsmetaL.g:12:9: 'asm'
            {
            match("asm"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:13:7: ( 'module' )
            // InternalAsmetaL.g:13:9: 'module'
            {
            match("module"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:14:7: ( 'main' )
            // InternalAsmetaL.g:14:9: 'main'
            {
            match("main"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:15:7: ( 'default' )
            // InternalAsmetaL.g:15:9: 'default'
            {
            match("default"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:16:7: ( 'import' )
            // InternalAsmetaL.g:16:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:17:7: ( '(' )
            // InternalAsmetaL.g:17:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:18:7: ( ',' )
            // InternalAsmetaL.g:18:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:19:7: ( ')' )
            // InternalAsmetaL.g:19:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:20:7: ( 'export' )
            // InternalAsmetaL.g:20:9: 'export'
            {
            match("export"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:21:7: ( '*' )
            // InternalAsmetaL.g:21:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:22:7: ( 'signature' )
            // InternalAsmetaL.g:22:9: 'signature'
            {
            match("signature"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:23:7: ( ':' )
            // InternalAsmetaL.g:23:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:24:7: ( 'init' )
            // InternalAsmetaL.g:24:9: 'init'
            {
            match("init"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:25:7: ( 'domain' )
            // InternalAsmetaL.g:25:9: 'domain'
            {
            match("domain"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:26:7: ( '=' )
            // InternalAsmetaL.g:26:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:27:7: ( 'function' )
            // InternalAsmetaL.g:27:9: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:28:7: ( 'in' )
            // InternalAsmetaL.g:28:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:29:7: ( 'agent' )
            // InternalAsmetaL.g:29:9: 'agent'
            {
            match("agent"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:30:7: ( 'Agent' )
            // InternalAsmetaL.g:30:9: 'Agent'
            {
            match("Agent"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:31:7: ( 'definitions' )
            // InternalAsmetaL.g:31:9: 'definitions'
            {
            match("definitions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:32:7: ( 'macro' )
            // InternalAsmetaL.g:32:9: 'macro'
            {
            match("macro"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:33:7: ( 'rule' )
            // InternalAsmetaL.g:33:9: 'rule'
            {
            match("rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:34:7: ( 'turbo' )
            // InternalAsmetaL.g:34:9: 'turbo'
            {
            match("turbo"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:35:7: ( 'invariant' )
            // InternalAsmetaL.g:35:9: 'invariant'
            {
            match("invariant"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:36:7: ( 'over' )
            // InternalAsmetaL.g:36:9: 'over'
            {
            match("over"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:37:7: ( 'CTL' )
            // InternalAsmetaL.g:37:9: 'CTL'
            {
            match("CTL"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:38:7: ( 'CTLSPEC' )
            // InternalAsmetaL.g:38:9: 'CTLSPEC'
            {
            match("CTLSPEC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:39:7: ( 'LTL' )
            // InternalAsmetaL.g:39:9: 'LTL'
            {
            match("LTL"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:40:7: ( 'LTLSPEC' )
            // InternalAsmetaL.g:40:9: 'LTLSPEC'
            {
            match("LTLSPEC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:41:7: ( 'JUSTICE' )
            // InternalAsmetaL.g:41:9: 'JUSTICE'
            {
            match("JUSTICE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:42:7: ( 'COMPASSION' )
            // InternalAsmetaL.g:42:9: 'COMPASSION'
            {
            match("COMPASSION"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:43:7: ( 'INVAR' )
            // InternalAsmetaL.g:43:9: 'INVAR'
            {
            match("INVAR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:44:7: ( 'dynamic' )
            // InternalAsmetaL.g:44:9: 'dynamic'
            {
            match("dynamic"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:45:7: ( 'subsetof' )
            // InternalAsmetaL.g:45:9: 'subsetof'
            {
            match("subsetof"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:46:7: ( 'anydomain' )
            // InternalAsmetaL.g:46:9: 'anydomain'
            {
            match("anydomain"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:47:7: ( 'basic' )
            // InternalAsmetaL.g:47:9: 'basic'
            {
            match("basic"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:48:7: ( 'Integer' )
            // InternalAsmetaL.g:48:9: 'Integer'
            {
            match("Integer"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:49:7: ( 'Real' )
            // InternalAsmetaL.g:49:9: 'Real'
            {
            match("Real"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:50:7: ( 'String' )
            // InternalAsmetaL.g:50:9: 'String'
            {
            match("String"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:51:7: ( 'Natural' )
            // InternalAsmetaL.g:51:9: 'Natural'
            {
            match("Natural"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:52:7: ( 'Char' )
            // InternalAsmetaL.g:52:9: 'Char'
            {
            match("Char"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:53:7: ( 'Complex' )
            // InternalAsmetaL.g:53:9: 'Complex'
            {
            match("Complex"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:54:7: ( 'Boolean' )
            // InternalAsmetaL.g:54:9: 'Boolean'
            {
            match("Boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:55:7: ( 'Undef' )
            // InternalAsmetaL.g:55:9: 'Undef'
            {
            match("Undef"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:56:7: ( 'abstract' )
            // InternalAsmetaL.g:56:9: 'abstract'
            {
            match("abstract"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:57:7: ( 'Reserve' )
            // InternalAsmetaL.g:57:9: 'Reserve'
            {
            match("Reserve"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:58:7: ( 'enum' )
            // InternalAsmetaL.g:58:9: 'enum'
            {
            match("enum"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:59:7: ( '{' )
            // InternalAsmetaL.g:59:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:60:7: ( '|' )
            // InternalAsmetaL.g:60:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:61:7: ( '}' )
            // InternalAsmetaL.g:61:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:62:7: ( 'Rule' )
            // InternalAsmetaL.g:62:9: 'Rule'
            {
            match("Rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:63:7: ( 'Prod' )
            // InternalAsmetaL.g:63:9: 'Prod'
            {
            match("Prod"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:64:7: ( 'Seq' )
            // InternalAsmetaL.g:64:9: 'Seq'
            {
            match("Seq"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:65:7: ( 'Powerset' )
            // InternalAsmetaL.g:65:9: 'Powerset'
            {
            match("Powerset"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:66:7: ( 'Bag' )
            // InternalAsmetaL.g:66:9: 'Bag'
            {
            match("Bag"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:67:7: ( 'Map' )
            // InternalAsmetaL.g:67:9: 'Map'
            {
            match("Map"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:68:7: ( 'derived' )
            // InternalAsmetaL.g:68:9: 'derived'
            {
            match("derived"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:69:7: ( '->' )
            // InternalAsmetaL.g:69:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:70:7: ( 'static' )
            // InternalAsmetaL.g:70:9: 'static'
            {
            match("static"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:71:7: ( 'local' )
            // InternalAsmetaL.g:71:9: 'local'
            {
            match("local"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:72:7: ( 'controlled' )
            // InternalAsmetaL.g:72:9: 'controlled'
            {
            match("controlled"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:73:7: ( 'shared' )
            // InternalAsmetaL.g:73:9: 'shared'
            {
            match("shared"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:74:7: ( 'monitored' )
            // InternalAsmetaL.g:74:9: 'monitored'
            {
            match("monitored"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:75:7: ( 'out' )
            // InternalAsmetaL.g:75:9: 'out'
            {
            match("out"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:76:7: ( 'and' )
            // InternalAsmetaL.g:76:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:77:7: ( 'or' )
            // InternalAsmetaL.g:77:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:78:7: ( 'not' )
            // InternalAsmetaL.g:78:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:79:7: ( 'xor' )
            // InternalAsmetaL.g:79:9: 'xor'
            {
            match("xor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:80:7: ( 'mod' )
            // InternalAsmetaL.g:80:9: 'mod'
            {
            match("mod"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:81:7: ( 'iff' )
            // InternalAsmetaL.g:81:9: 'iff'
            {
            match("iff"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:82:7: ( 'implies' )
            // InternalAsmetaL.g:82:9: 'implies'
            {
            match("implies"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:83:7: ( 'while' )
            // InternalAsmetaL.g:83:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:84:7: ( 'for' )
            // InternalAsmetaL.g:84:9: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:85:7: ( '!=' )
            // InternalAsmetaL.g:85:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:86:7: ( '>' )
            // InternalAsmetaL.g:86:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:87:7: ( '<' )
            // InternalAsmetaL.g:87:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:88:8: ( '>=' )
            // InternalAsmetaL.g:88:10: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:89:8: ( '<=' )
            // InternalAsmetaL.g:89:10: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:90:8: ( '+' )
            // InternalAsmetaL.g:90:10: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:91:8: ( '-' )
            // InternalAsmetaL.g:91:10: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:92:8: ( '/' )
            // InternalAsmetaL.g:92:10: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:93:8: ( '^' )
            // InternalAsmetaL.g:93:10: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:94:8: ( '.' )
            // InternalAsmetaL.g:94:10: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "T__107"
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:95:8: ( 'true' )
            // InternalAsmetaL.g:95:10: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__107"

    // $ANTLR start "T__108"
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:96:8: ( 'false' )
            // InternalAsmetaL.g:96:10: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__108"

    // $ANTLR start "T__109"
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:97:8: ( 'undef' )
            // InternalAsmetaL.g:97:10: 'undef'
            {
            match("undef"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__109"

    // $ANTLR start "T__110"
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:98:8: ( 'if' )
            // InternalAsmetaL.g:98:10: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__110"

    // $ANTLR start "T__111"
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:99:8: ( 'then' )
            // InternalAsmetaL.g:99:10: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__111"

    // $ANTLR start "T__112"
    public final void mT__112() throws RecognitionException {
        try {
            int _type = T__112;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:100:8: ( 'else' )
            // InternalAsmetaL.g:100:10: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__112"

    // $ANTLR start "T__113"
    public final void mT__113() throws RecognitionException {
        try {
            int _type = T__113;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:101:8: ( 'endif' )
            // InternalAsmetaL.g:101:10: 'endif'
            {
            match("endif"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__113"

    // $ANTLR start "T__114"
    public final void mT__114() throws RecognitionException {
        try {
            int _type = T__114;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:102:8: ( 'switch' )
            // InternalAsmetaL.g:102:10: 'switch'
            {
            match("switch"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__114"

    // $ANTLR start "T__115"
    public final void mT__115() throws RecognitionException {
        try {
            int _type = T__115;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:103:8: ( 'case' )
            // InternalAsmetaL.g:103:10: 'case'
            {
            match("case"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__115"

    // $ANTLR start "T__116"
    public final void mT__116() throws RecognitionException {
        try {
            int _type = T__116;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:104:8: ( 'otherwise' )
            // InternalAsmetaL.g:104:10: 'otherwise'
            {
            match("otherwise"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__116"

    // $ANTLR start "T__117"
    public final void mT__117() throws RecognitionException {
        try {
            int _type = T__117;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:105:8: ( 'endswitch' )
            // InternalAsmetaL.g:105:10: 'endswitch'
            {
            match("endswitch"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__117"

    // $ANTLR start "T__118"
    public final void mT__118() throws RecognitionException {
        try {
            int _type = T__118;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:106:8: ( '[' )
            // InternalAsmetaL.g:106:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__118"

    // $ANTLR start "T__119"
    public final void mT__119() throws RecognitionException {
        try {
            int _type = T__119;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:107:8: ( ']' )
            // InternalAsmetaL.g:107:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__119"

    // $ANTLR start "T__120"
    public final void mT__120() throws RecognitionException {
        try {
            int _type = T__120;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:108:8: ( 'exist' )
            // InternalAsmetaL.g:108:10: 'exist'
            {
            match("exist"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__120"

    // $ANTLR start "T__121"
    public final void mT__121() throws RecognitionException {
        try {
            int _type = T__121;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:109:8: ( 'unique' )
            // InternalAsmetaL.g:109:10: 'unique'
            {
            match("unique"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__121"

    // $ANTLR start "T__122"
    public final void mT__122() throws RecognitionException {
        try {
            int _type = T__122;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:110:8: ( 'with' )
            // InternalAsmetaL.g:110:10: 'with'
            {
            match("with"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__122"

    // $ANTLR start "T__123"
    public final void mT__123() throws RecognitionException {
        try {
            int _type = T__123;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:111:8: ( 'forall' )
            // InternalAsmetaL.g:111:10: 'forall'
            {
            match("forall"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__123"

    // $ANTLR start "T__124"
    public final void mT__124() throws RecognitionException {
        try {
            int _type = T__124;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:112:8: ( 'let' )
            // InternalAsmetaL.g:112:10: 'let'
            {
            match("let"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__124"

    // $ANTLR start "T__125"
    public final void mT__125() throws RecognitionException {
        try {
            int _type = T__125;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:113:8: ( 'endlet' )
            // InternalAsmetaL.g:113:10: 'endlet'
            {
            match("endlet"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__125"

    // $ANTLR start "T__126"
    public final void mT__126() throws RecognitionException {
        try {
            int _type = T__126;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:114:8: ( '<<' )
            // InternalAsmetaL.g:114:10: '<<'
            {
            match("<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__126"

    // $ANTLR start "T__127"
    public final void mT__127() throws RecognitionException {
        try {
            int _type = T__127;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:115:8: ( '>>' )
            // InternalAsmetaL.g:115:10: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__127"

    // $ANTLR start "T__128"
    public final void mT__128() throws RecognitionException {
        try {
            int _type = T__128;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:116:8: ( 'skip' )
            // InternalAsmetaL.g:116:10: 'skip'
            {
            match("skip"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__128"

    // $ANTLR start "T__129"
    public final void mT__129() throws RecognitionException {
        try {
            int _type = T__129;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:117:8: ( ':=' )
            // InternalAsmetaL.g:117:10: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__129"

    // $ANTLR start "T__130"
    public final void mT__130() throws RecognitionException {
        try {
            int _type = T__130;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:118:8: ( 'par' )
            // InternalAsmetaL.g:118:10: 'par'
            {
            match("par"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__130"

    // $ANTLR start "T__131"
    public final void mT__131() throws RecognitionException {
        try {
            int _type = T__131;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:119:8: ( 'endpar' )
            // InternalAsmetaL.g:119:10: 'endpar'
            {
            match("endpar"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__131"

    // $ANTLR start "T__132"
    public final void mT__132() throws RecognitionException {
        try {
            int _type = T__132;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:120:8: ( 'choose' )
            // InternalAsmetaL.g:120:10: 'choose'
            {
            match("choose"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__132"

    // $ANTLR start "T__133"
    public final void mT__133() throws RecognitionException {
        try {
            int _type = T__133;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:121:8: ( 'do' )
            // InternalAsmetaL.g:121:10: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__133"

    // $ANTLR start "T__134"
    public final void mT__134() throws RecognitionException {
        try {
            int _type = T__134;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:122:8: ( 'ifnone' )
            // InternalAsmetaL.g:122:10: 'ifnone'
            {
            match("ifnone"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__134"

    // $ANTLR start "T__135"
    public final void mT__135() throws RecognitionException {
        try {
            int _type = T__135;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:123:8: ( 'extend' )
            // InternalAsmetaL.g:123:10: 'extend'
            {
            match("extend"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__135"

    // $ANTLR start "T__136"
    public final void mT__136() throws RecognitionException {
        try {
            int _type = T__136;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:124:8: ( 'seq' )
            // InternalAsmetaL.g:124:10: 'seq'
            {
            match("seq"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__136"

    // $ANTLR start "T__137"
    public final void mT__137() throws RecognitionException {
        try {
            int _type = T__137;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:125:8: ( 'endseq' )
            // InternalAsmetaL.g:125:10: 'endseq'
            {
            match("endseq"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__137"

    // $ANTLR start "T__138"
    public final void mT__138() throws RecognitionException {
        try {
            int _type = T__138;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:126:8: ( 'iterate' )
            // InternalAsmetaL.g:126:10: 'iterate'
            {
            match("iterate"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__138"

    // $ANTLR start "T__139"
    public final void mT__139() throws RecognitionException {
        try {
            int _type = T__139;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:127:8: ( 'enditerate' )
            // InternalAsmetaL.g:127:10: 'enditerate'
            {
            match("enditerate"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__139"

    // $ANTLR start "T__140"
    public final void mT__140() throws RecognitionException {
        try {
            int _type = T__140;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:128:8: ( '<-' )
            // InternalAsmetaL.g:128:10: '<-'
            {
            match("<-"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__140"

    // $ANTLR start "T__141"
    public final void mT__141() throws RecognitionException {
        try {
            int _type = T__141;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:129:8: ( 'whilerec' )
            // InternalAsmetaL.g:129:10: 'whilerec'
            {
            match("whilerec"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__141"

    // $ANTLR start "T__142"
    public final void mT__142() throws RecognitionException {
        try {
            int _type = T__142;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:130:8: ( '../' )
            // InternalAsmetaL.g:130:10: '../'
            {
            match("../"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__142"

    // $ANTLR start "T__143"
    public final void mT__143() throws RecognitionException {
        try {
            int _type = T__143;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:131:8: ( './' )
            // InternalAsmetaL.g:131:10: './'
            {
            match("./"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__143"

    // $ANTLR start "T__144"
    public final void mT__144() throws RecognitionException {
        try {
            int _type = T__144;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:132:8: ( '$' )
            // InternalAsmetaL.g:132:10: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__144"

    // $ANTLR start "RULE_DIGIT"
    public final void mRULE_DIGIT() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12017:21: ( '0' .. '9' )
            // InternalAsmetaL.g:12017:23: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGIT"

    // $ANTLR start "RULE_NUMBER_TOKEN"
    public final void mRULE_NUMBER_TOKEN() throws RecognitionException {
        try {
            int _type = RULE_NUMBER_TOKEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12019:19: ( ( RULE_DIGIT )+ )
            // InternalAsmetaL.g:12019:21: ( RULE_DIGIT )+
            {
            // InternalAsmetaL.g:12019:21: ( RULE_DIGIT )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAsmetaL.g:12019:21: RULE_DIGIT
            	    {
            	    mRULE_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NUMBER_TOKEN"

    // $ANTLR start "RULE_NATNUMBER"
    public final void mRULE_NATNUMBER() throws RecognitionException {
        try {
            int _type = RULE_NATNUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12021:16: ( RULE_NUMBER_TOKEN 'n' )
            // InternalAsmetaL.g:12021:18: RULE_NUMBER_TOKEN 'n'
            {
            mRULE_NUMBER_TOKEN(); 
            match('n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NATNUMBER"

    // $ANTLR start "RULE_REAL_NUMBER"
    public final void mRULE_REAL_NUMBER() throws RecognitionException {
        try {
            int _type = RULE_REAL_NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12023:18: ( RULE_NUMBER_TOKEN '.' RULE_NUMBER_TOKEN )
            // InternalAsmetaL.g:12023:20: RULE_NUMBER_TOKEN '.' RULE_NUMBER_TOKEN
            {
            mRULE_NUMBER_TOKEN(); 
            match('.'); 
            mRULE_NUMBER_TOKEN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_REAL_NUMBER"

    // $ANTLR start "RULE_MAIUSC_ID"
    public final void mRULE_MAIUSC_ID() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12025:25: ( 'A' .. 'Z' )
            // InternalAsmetaL.g:12025:27: 'A' .. 'Z'
            {
            matchRange('A','Z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_MAIUSC_ID"

    // $ANTLR start "RULE_MIN_ID"
    public final void mRULE_MIN_ID() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12027:22: ( 'a' .. 'z' )
            // InternalAsmetaL.g:12027:24: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_MIN_ID"

    // $ANTLR start "RULE_ACCENT_CHR"
    public final void mRULE_ACCENT_CHR() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12029:26: ( ( '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00F2' | '\\u00EC' | '\\u00F9' | '\\u00C3' ) )
            // InternalAsmetaL.g:12029:28: ( '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00F2' | '\\u00EC' | '\\u00F9' | '\\u00C3' )
            {
            if ( input.LA(1)=='\u00C3'||input.LA(1)=='\u00E0'||(input.LA(1)>='\u00E8' && input.LA(1)<='\u00E9')||input.LA(1)=='\u00EC'||input.LA(1)=='\u00F2'||input.LA(1)=='\u00F9' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ACCENT_CHR"

    // $ANTLR start "RULE_ENUM_ID"
    public final void mRULE_ENUM_ID() throws RecognitionException {
        try {
            int _type = RULE_ENUM_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12031:14: ( ( '^' )? RULE_MAIUSC_ID RULE_MAIUSC_ID ( RULE_MAIUSC_ID | RULE_DIGIT | '_' )* )
            // InternalAsmetaL.g:12031:16: ( '^' )? RULE_MAIUSC_ID RULE_MAIUSC_ID ( RULE_MAIUSC_ID | RULE_DIGIT | '_' )*
            {
            // InternalAsmetaL.g:12031:16: ( '^' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='^') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalAsmetaL.g:12031:16: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            mRULE_MAIUSC_ID(); 
            mRULE_MAIUSC_ID(); 
            // InternalAsmetaL.g:12031:51: ( RULE_MAIUSC_ID | RULE_DIGIT | '_' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalAsmetaL.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ENUM_ID"

    // $ANTLR start "RULE_SPECIAL_CHAR"
    public final void mRULE_SPECIAL_CHAR() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12033:28: ( ( '!' | '.' | ',' | ':' | '-' | '+' | '$' | '%' | '(' | ')' | '[' | ']' | '=' | '?' | '^' | '_' | ';' | '\\u00A8' | '@' | '>' | '<' | '|' | '\\\\' | '/' ) )
            // InternalAsmetaL.g:12033:30: ( '!' | '.' | ',' | ':' | '-' | '+' | '$' | '%' | '(' | ')' | '[' | ']' | '=' | '?' | '^' | '_' | ';' | '\\u00A8' | '@' | '>' | '<' | '|' | '\\\\' | '/' )
            {
            if ( input.LA(1)=='!'||(input.LA(1)>='$' && input.LA(1)<='%')||(input.LA(1)>='(' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='/')||(input.LA(1)>=':' && input.LA(1)<='@')||(input.LA(1)>='[' && input.LA(1)<='_')||input.LA(1)=='|'||input.LA(1)=='\u00A8' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_SPECIAL_CHAR"

    // $ANTLR start "RULE_RULE_ID"
    public final void mRULE_RULE_ID() throws RecognitionException {
        try {
            int _type = RULE_RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12035:14: ( 'r_' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )* )
            // InternalAsmetaL.g:12035:16: 'r_' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )*
            {
            match("r_"); 

            // InternalAsmetaL.g:12035:21: ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalAsmetaL.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_RULE_ID"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12037:9: ( ( '^' )? ( RULE_MAIUSC_ID | RULE_MIN_ID | '_' ) ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )* )
            // InternalAsmetaL.g:12037:11: ( '^' )? ( RULE_MAIUSC_ID | RULE_MIN_ID | '_' ) ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )*
            {
            // InternalAsmetaL.g:12037:11: ( '^' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='^') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalAsmetaL.g:12037:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalAsmetaL.g:12037:49: ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | '_' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalAsmetaL.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_CHAR_LITERAL"
    public final void mRULE_CHAR_LITERAL() throws RecognitionException {
        try {
            int _type = RULE_CHAR_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12039:19: ( '\\'' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )? '\\'' )
            // InternalAsmetaL.g:12039:21: '\\'' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )? '\\''
            {
            match('\''); 
            // InternalAsmetaL.g:12039:26: ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='!'||(LA7_0>='$' && LA7_0<='%')||(LA7_0>='(' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='_')||(LA7_0>='a' && LA7_0<='z')||LA7_0=='|'||LA7_0=='\u00A8'||LA7_0=='\u00C3'||LA7_0=='\u00E0'||(LA7_0>='\u00E8' && LA7_0<='\u00E9')||LA7_0=='\u00EC'||LA7_0=='\u00F2'||LA7_0=='\u00F9') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalAsmetaL.g:
                    {
                    if ( input.LA(1)=='!'||(input.LA(1)>='$' && input.LA(1)<='%')||(input.LA(1)>='(' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='|'||input.LA(1)=='\u00A8'||input.LA(1)=='\u00C3'||input.LA(1)=='\u00E0'||(input.LA(1)>='\u00E8' && input.LA(1)<='\u00E9')||input.LA(1)=='\u00EC'||input.LA(1)=='\u00F2'||input.LA(1)=='\u00F9' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CHAR_LITERAL"

    // $ANTLR start "RULE_STRING_LITERAL"
    public final void mRULE_STRING_LITERAL() throws RecognitionException {
        try {
            int _type = RULE_STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12041:21: ( '\"' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_WS | '\\'' | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )* '\"' )
            // InternalAsmetaL.g:12041:23: '\"' ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_WS | '\\'' | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )* '\"'
            {
            match('\"'); 
            // InternalAsmetaL.g:12041:27: ( RULE_MAIUSC_ID | RULE_MIN_ID | RULE_DIGIT | RULE_WS | '\\'' | RULE_SPECIAL_CHAR | RULE_ACCENT_CHR )*
            loop8:
            do {
                int alt8=8;
                switch ( input.LA(1) ) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    {
                    alt8=1;
                    }
                    break;
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt8=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt8=3;
                    }
                    break;
                case '\t':
                case '\n':
                case '\r':
                case ' ':
                    {
                    alt8=4;
                    }
                    break;
                case '\'':
                    {
                    alt8=5;
                    }
                    break;
                case '!':
                case '$':
                case '%':
                case '(':
                case ')':
                case '+':
                case ',':
                case '-':
                case '.':
                case '/':
                case ':':
                case ';':
                case '<':
                case '=':
                case '>':
                case '?':
                case '@':
                case '[':
                case '\\':
                case ']':
                case '^':
                case '_':
                case '|':
                case '\u00A8':
                    {
                    alt8=6;
                    }
                    break;
                case '\u00C3':
                case '\u00E0':
                case '\u00E8':
                case '\u00E9':
                case '\u00EC':
                case '\u00F2':
                case '\u00F9':
                    {
                    alt8=7;
                    }
                    break;

                }

                switch (alt8) {
            	case 1 :
            	    // InternalAsmetaL.g:12041:28: RULE_MAIUSC_ID
            	    {
            	    mRULE_MAIUSC_ID(); 

            	    }
            	    break;
            	case 2 :
            	    // InternalAsmetaL.g:12041:43: RULE_MIN_ID
            	    {
            	    mRULE_MIN_ID(); 

            	    }
            	    break;
            	case 3 :
            	    // InternalAsmetaL.g:12041:55: RULE_DIGIT
            	    {
            	    mRULE_DIGIT(); 

            	    }
            	    break;
            	case 4 :
            	    // InternalAsmetaL.g:12041:66: RULE_WS
            	    {
            	    mRULE_WS(); 

            	    }
            	    break;
            	case 5 :
            	    // InternalAsmetaL.g:12041:74: '\\''
            	    {
            	    match('\''); 

            	    }
            	    break;
            	case 6 :
            	    // InternalAsmetaL.g:12041:79: RULE_SPECIAL_CHAR
            	    {
            	    mRULE_SPECIAL_CHAR(); 

            	    }
            	    break;
            	case 7 :
            	    // InternalAsmetaL.g:12041:97: RULE_ACCENT_CHR
            	    {
            	    mRULE_ACCENT_CHR(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING_LITERAL"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12043:13: ( '\"' (~ ( '\"' ) )* '\"' )
            // InternalAsmetaL.g:12043:15: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // InternalAsmetaL.g:12043:19: (~ ( '\"' ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\u0000' && LA9_0<='!')||(LA9_0>='#' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalAsmetaL.g:12043:19: ~ ( '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_COMPLEX_NUMBER"
    public final void mRULE_COMPLEX_NUMBER() throws RecognitionException {
        try {
            int _type = RULE_COMPLEX_NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12045:21: ( RULE_IMMAGINARY_NUMBER )
            // InternalAsmetaL.g:12045:23: RULE_IMMAGINARY_NUMBER
            {
            mRULE_IMMAGINARY_NUMBER(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_COMPLEX_NUMBER"

    // $ANTLR start "RULE_IMMAGINARY_NUMBER"
    public final void mRULE_IMMAGINARY_NUMBER() throws RecognitionException {
        try {
            // InternalAsmetaL.g:12047:33: ( 'i' ( RULE_DIGIT )+ ( '.' ( RULE_DIGIT )+ )? )
            // InternalAsmetaL.g:12047:35: 'i' ( RULE_DIGIT )+ ( '.' ( RULE_DIGIT )+ )?
            {
            match('i'); 
            // InternalAsmetaL.g:12047:39: ( RULE_DIGIT )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalAsmetaL.g:12047:39: RULE_DIGIT
            	    {
            	    mRULE_DIGIT(); 

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

            // InternalAsmetaL.g:12047:51: ( '.' ( RULE_DIGIT )+ )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='.') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalAsmetaL.g:12047:52: '.' ( RULE_DIGIT )+
                    {
                    match('.'); 
                    // InternalAsmetaL.g:12047:56: ( RULE_DIGIT )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // InternalAsmetaL.g:12047:56: RULE_DIGIT
                    	    {
                    	    mRULE_DIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);


                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_IMMAGINARY_NUMBER"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12049:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalAsmetaL.g:12049:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalAsmetaL.g:12049:24: ( options {greedy=false; } : . )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='*') ) {
                    int LA13_1 = input.LA(2);

                    if ( (LA13_1=='/') ) {
                        alt13=2;
                    }
                    else if ( ((LA13_1>='\u0000' && LA13_1<='.')||(LA13_1>='0' && LA13_1<='\uFFFF')) ) {
                        alt13=1;
                    }


                }
                else if ( ((LA13_0>='\u0000' && LA13_0<=')')||(LA13_0>='+' && LA13_0<='\uFFFF')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalAsmetaL.g:12049:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12051:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )* '\\n' )? )
            // InternalAsmetaL.g:12051:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )* '\\n' )?
            {
            match("//"); 

            // InternalAsmetaL.g:12051:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\u0000' && LA14_0<='\t')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='\uFFFF')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalAsmetaL.g:12051:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // InternalAsmetaL.g:12051:40: ( ( '\\r' )* '\\n' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\n'||LA16_0=='\r') ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalAsmetaL.g:12051:41: ( '\\r' )* '\\n'
                    {
                    // InternalAsmetaL.g:12051:41: ( '\\r' )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0=='\r') ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalAsmetaL.g:12051:41: '\\r'
                    	    {
                    	    match('\r'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAsmetaL.g:12053:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalAsmetaL.g:12053:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalAsmetaL.g:12053:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\t' && LA17_0<='\n')||LA17_0=='\r'||LA17_0==' ') ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalAsmetaL.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    public void mTokens() throws RecognitionException {
        // InternalAsmetaL.g:1:8: ( T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | T__139 | T__140 | T__141 | T__142 | T__143 | T__144 | RULE_NUMBER_TOKEN | RULE_NATNUMBER | RULE_REAL_NUMBER | RULE_ENUM_ID | RULE_RULE_ID | RULE_ID | RULE_CHAR_LITERAL | RULE_STRING_LITERAL | RULE_STRING | RULE_COMPLEX_NUMBER | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS )
        int alt18=135;
        alt18 = dfa18.predict(input);
        switch (alt18) {
            case 1 :
                // InternalAsmetaL.g:1:10: T__23
                {
                mT__23(); 

                }
                break;
            case 2 :
                // InternalAsmetaL.g:1:16: T__24
                {
                mT__24(); 

                }
                break;
            case 3 :
                // InternalAsmetaL.g:1:22: T__25
                {
                mT__25(); 

                }
                break;
            case 4 :
                // InternalAsmetaL.g:1:28: T__26
                {
                mT__26(); 

                }
                break;
            case 5 :
                // InternalAsmetaL.g:1:34: T__27
                {
                mT__27(); 

                }
                break;
            case 6 :
                // InternalAsmetaL.g:1:40: T__28
                {
                mT__28(); 

                }
                break;
            case 7 :
                // InternalAsmetaL.g:1:46: T__29
                {
                mT__29(); 

                }
                break;
            case 8 :
                // InternalAsmetaL.g:1:52: T__30
                {
                mT__30(); 

                }
                break;
            case 9 :
                // InternalAsmetaL.g:1:58: T__31
                {
                mT__31(); 

                }
                break;
            case 10 :
                // InternalAsmetaL.g:1:64: T__32
                {
                mT__32(); 

                }
                break;
            case 11 :
                // InternalAsmetaL.g:1:70: T__33
                {
                mT__33(); 

                }
                break;
            case 12 :
                // InternalAsmetaL.g:1:76: T__34
                {
                mT__34(); 

                }
                break;
            case 13 :
                // InternalAsmetaL.g:1:82: T__35
                {
                mT__35(); 

                }
                break;
            case 14 :
                // InternalAsmetaL.g:1:88: T__36
                {
                mT__36(); 

                }
                break;
            case 15 :
                // InternalAsmetaL.g:1:94: T__37
                {
                mT__37(); 

                }
                break;
            case 16 :
                // InternalAsmetaL.g:1:100: T__38
                {
                mT__38(); 

                }
                break;
            case 17 :
                // InternalAsmetaL.g:1:106: T__39
                {
                mT__39(); 

                }
                break;
            case 18 :
                // InternalAsmetaL.g:1:112: T__40
                {
                mT__40(); 

                }
                break;
            case 19 :
                // InternalAsmetaL.g:1:118: T__41
                {
                mT__41(); 

                }
                break;
            case 20 :
                // InternalAsmetaL.g:1:124: T__42
                {
                mT__42(); 

                }
                break;
            case 21 :
                // InternalAsmetaL.g:1:130: T__43
                {
                mT__43(); 

                }
                break;
            case 22 :
                // InternalAsmetaL.g:1:136: T__44
                {
                mT__44(); 

                }
                break;
            case 23 :
                // InternalAsmetaL.g:1:142: T__45
                {
                mT__45(); 

                }
                break;
            case 24 :
                // InternalAsmetaL.g:1:148: T__46
                {
                mT__46(); 

                }
                break;
            case 25 :
                // InternalAsmetaL.g:1:154: T__47
                {
                mT__47(); 

                }
                break;
            case 26 :
                // InternalAsmetaL.g:1:160: T__48
                {
                mT__48(); 

                }
                break;
            case 27 :
                // InternalAsmetaL.g:1:166: T__49
                {
                mT__49(); 

                }
                break;
            case 28 :
                // InternalAsmetaL.g:1:172: T__50
                {
                mT__50(); 

                }
                break;
            case 29 :
                // InternalAsmetaL.g:1:178: T__51
                {
                mT__51(); 

                }
                break;
            case 30 :
                // InternalAsmetaL.g:1:184: T__52
                {
                mT__52(); 

                }
                break;
            case 31 :
                // InternalAsmetaL.g:1:190: T__53
                {
                mT__53(); 

                }
                break;
            case 32 :
                // InternalAsmetaL.g:1:196: T__54
                {
                mT__54(); 

                }
                break;
            case 33 :
                // InternalAsmetaL.g:1:202: T__55
                {
                mT__55(); 

                }
                break;
            case 34 :
                // InternalAsmetaL.g:1:208: T__56
                {
                mT__56(); 

                }
                break;
            case 35 :
                // InternalAsmetaL.g:1:214: T__57
                {
                mT__57(); 

                }
                break;
            case 36 :
                // InternalAsmetaL.g:1:220: T__58
                {
                mT__58(); 

                }
                break;
            case 37 :
                // InternalAsmetaL.g:1:226: T__59
                {
                mT__59(); 

                }
                break;
            case 38 :
                // InternalAsmetaL.g:1:232: T__60
                {
                mT__60(); 

                }
                break;
            case 39 :
                // InternalAsmetaL.g:1:238: T__61
                {
                mT__61(); 

                }
                break;
            case 40 :
                // InternalAsmetaL.g:1:244: T__62
                {
                mT__62(); 

                }
                break;
            case 41 :
                // InternalAsmetaL.g:1:250: T__63
                {
                mT__63(); 

                }
                break;
            case 42 :
                // InternalAsmetaL.g:1:256: T__64
                {
                mT__64(); 

                }
                break;
            case 43 :
                // InternalAsmetaL.g:1:262: T__65
                {
                mT__65(); 

                }
                break;
            case 44 :
                // InternalAsmetaL.g:1:268: T__66
                {
                mT__66(); 

                }
                break;
            case 45 :
                // InternalAsmetaL.g:1:274: T__67
                {
                mT__67(); 

                }
                break;
            case 46 :
                // InternalAsmetaL.g:1:280: T__68
                {
                mT__68(); 

                }
                break;
            case 47 :
                // InternalAsmetaL.g:1:286: T__69
                {
                mT__69(); 

                }
                break;
            case 48 :
                // InternalAsmetaL.g:1:292: T__70
                {
                mT__70(); 

                }
                break;
            case 49 :
                // InternalAsmetaL.g:1:298: T__71
                {
                mT__71(); 

                }
                break;
            case 50 :
                // InternalAsmetaL.g:1:304: T__72
                {
                mT__72(); 

                }
                break;
            case 51 :
                // InternalAsmetaL.g:1:310: T__73
                {
                mT__73(); 

                }
                break;
            case 52 :
                // InternalAsmetaL.g:1:316: T__74
                {
                mT__74(); 

                }
                break;
            case 53 :
                // InternalAsmetaL.g:1:322: T__75
                {
                mT__75(); 

                }
                break;
            case 54 :
                // InternalAsmetaL.g:1:328: T__76
                {
                mT__76(); 

                }
                break;
            case 55 :
                // InternalAsmetaL.g:1:334: T__77
                {
                mT__77(); 

                }
                break;
            case 56 :
                // InternalAsmetaL.g:1:340: T__78
                {
                mT__78(); 

                }
                break;
            case 57 :
                // InternalAsmetaL.g:1:346: T__79
                {
                mT__79(); 

                }
                break;
            case 58 :
                // InternalAsmetaL.g:1:352: T__80
                {
                mT__80(); 

                }
                break;
            case 59 :
                // InternalAsmetaL.g:1:358: T__81
                {
                mT__81(); 

                }
                break;
            case 60 :
                // InternalAsmetaL.g:1:364: T__82
                {
                mT__82(); 

                }
                break;
            case 61 :
                // InternalAsmetaL.g:1:370: T__83
                {
                mT__83(); 

                }
                break;
            case 62 :
                // InternalAsmetaL.g:1:376: T__84
                {
                mT__84(); 

                }
                break;
            case 63 :
                // InternalAsmetaL.g:1:382: T__85
                {
                mT__85(); 

                }
                break;
            case 64 :
                // InternalAsmetaL.g:1:388: T__86
                {
                mT__86(); 

                }
                break;
            case 65 :
                // InternalAsmetaL.g:1:394: T__87
                {
                mT__87(); 

                }
                break;
            case 66 :
                // InternalAsmetaL.g:1:400: T__88
                {
                mT__88(); 

                }
                break;
            case 67 :
                // InternalAsmetaL.g:1:406: T__89
                {
                mT__89(); 

                }
                break;
            case 68 :
                // InternalAsmetaL.g:1:412: T__90
                {
                mT__90(); 

                }
                break;
            case 69 :
                // InternalAsmetaL.g:1:418: T__91
                {
                mT__91(); 

                }
                break;
            case 70 :
                // InternalAsmetaL.g:1:424: T__92
                {
                mT__92(); 

                }
                break;
            case 71 :
                // InternalAsmetaL.g:1:430: T__93
                {
                mT__93(); 

                }
                break;
            case 72 :
                // InternalAsmetaL.g:1:436: T__94
                {
                mT__94(); 

                }
                break;
            case 73 :
                // InternalAsmetaL.g:1:442: T__95
                {
                mT__95(); 

                }
                break;
            case 74 :
                // InternalAsmetaL.g:1:448: T__96
                {
                mT__96(); 

                }
                break;
            case 75 :
                // InternalAsmetaL.g:1:454: T__97
                {
                mT__97(); 

                }
                break;
            case 76 :
                // InternalAsmetaL.g:1:460: T__98
                {
                mT__98(); 

                }
                break;
            case 77 :
                // InternalAsmetaL.g:1:466: T__99
                {
                mT__99(); 

                }
                break;
            case 78 :
                // InternalAsmetaL.g:1:472: T__100
                {
                mT__100(); 

                }
                break;
            case 79 :
                // InternalAsmetaL.g:1:479: T__101
                {
                mT__101(); 

                }
                break;
            case 80 :
                // InternalAsmetaL.g:1:486: T__102
                {
                mT__102(); 

                }
                break;
            case 81 :
                // InternalAsmetaL.g:1:493: T__103
                {
                mT__103(); 

                }
                break;
            case 82 :
                // InternalAsmetaL.g:1:500: T__104
                {
                mT__104(); 

                }
                break;
            case 83 :
                // InternalAsmetaL.g:1:507: T__105
                {
                mT__105(); 

                }
                break;
            case 84 :
                // InternalAsmetaL.g:1:514: T__106
                {
                mT__106(); 

                }
                break;
            case 85 :
                // InternalAsmetaL.g:1:521: T__107
                {
                mT__107(); 

                }
                break;
            case 86 :
                // InternalAsmetaL.g:1:528: T__108
                {
                mT__108(); 

                }
                break;
            case 87 :
                // InternalAsmetaL.g:1:535: T__109
                {
                mT__109(); 

                }
                break;
            case 88 :
                // InternalAsmetaL.g:1:542: T__110
                {
                mT__110(); 

                }
                break;
            case 89 :
                // InternalAsmetaL.g:1:549: T__111
                {
                mT__111(); 

                }
                break;
            case 90 :
                // InternalAsmetaL.g:1:556: T__112
                {
                mT__112(); 

                }
                break;
            case 91 :
                // InternalAsmetaL.g:1:563: T__113
                {
                mT__113(); 

                }
                break;
            case 92 :
                // InternalAsmetaL.g:1:570: T__114
                {
                mT__114(); 

                }
                break;
            case 93 :
                // InternalAsmetaL.g:1:577: T__115
                {
                mT__115(); 

                }
                break;
            case 94 :
                // InternalAsmetaL.g:1:584: T__116
                {
                mT__116(); 

                }
                break;
            case 95 :
                // InternalAsmetaL.g:1:591: T__117
                {
                mT__117(); 

                }
                break;
            case 96 :
                // InternalAsmetaL.g:1:598: T__118
                {
                mT__118(); 

                }
                break;
            case 97 :
                // InternalAsmetaL.g:1:605: T__119
                {
                mT__119(); 

                }
                break;
            case 98 :
                // InternalAsmetaL.g:1:612: T__120
                {
                mT__120(); 

                }
                break;
            case 99 :
                // InternalAsmetaL.g:1:619: T__121
                {
                mT__121(); 

                }
                break;
            case 100 :
                // InternalAsmetaL.g:1:626: T__122
                {
                mT__122(); 

                }
                break;
            case 101 :
                // InternalAsmetaL.g:1:633: T__123
                {
                mT__123(); 

                }
                break;
            case 102 :
                // InternalAsmetaL.g:1:640: T__124
                {
                mT__124(); 

                }
                break;
            case 103 :
                // InternalAsmetaL.g:1:647: T__125
                {
                mT__125(); 

                }
                break;
            case 104 :
                // InternalAsmetaL.g:1:654: T__126
                {
                mT__126(); 

                }
                break;
            case 105 :
                // InternalAsmetaL.g:1:661: T__127
                {
                mT__127(); 

                }
                break;
            case 106 :
                // InternalAsmetaL.g:1:668: T__128
                {
                mT__128(); 

                }
                break;
            case 107 :
                // InternalAsmetaL.g:1:675: T__129
                {
                mT__129(); 

                }
                break;
            case 108 :
                // InternalAsmetaL.g:1:682: T__130
                {
                mT__130(); 

                }
                break;
            case 109 :
                // InternalAsmetaL.g:1:689: T__131
                {
                mT__131(); 

                }
                break;
            case 110 :
                // InternalAsmetaL.g:1:696: T__132
                {
                mT__132(); 

                }
                break;
            case 111 :
                // InternalAsmetaL.g:1:703: T__133
                {
                mT__133(); 

                }
                break;
            case 112 :
                // InternalAsmetaL.g:1:710: T__134
                {
                mT__134(); 

                }
                break;
            case 113 :
                // InternalAsmetaL.g:1:717: T__135
                {
                mT__135(); 

                }
                break;
            case 114 :
                // InternalAsmetaL.g:1:724: T__136
                {
                mT__136(); 

                }
                break;
            case 115 :
                // InternalAsmetaL.g:1:731: T__137
                {
                mT__137(); 

                }
                break;
            case 116 :
                // InternalAsmetaL.g:1:738: T__138
                {
                mT__138(); 

                }
                break;
            case 117 :
                // InternalAsmetaL.g:1:745: T__139
                {
                mT__139(); 

                }
                break;
            case 118 :
                // InternalAsmetaL.g:1:752: T__140
                {
                mT__140(); 

                }
                break;
            case 119 :
                // InternalAsmetaL.g:1:759: T__141
                {
                mT__141(); 

                }
                break;
            case 120 :
                // InternalAsmetaL.g:1:766: T__142
                {
                mT__142(); 

                }
                break;
            case 121 :
                // InternalAsmetaL.g:1:773: T__143
                {
                mT__143(); 

                }
                break;
            case 122 :
                // InternalAsmetaL.g:1:780: T__144
                {
                mT__144(); 

                }
                break;
            case 123 :
                // InternalAsmetaL.g:1:787: RULE_NUMBER_TOKEN
                {
                mRULE_NUMBER_TOKEN(); 

                }
                break;
            case 124 :
                // InternalAsmetaL.g:1:805: RULE_NATNUMBER
                {
                mRULE_NATNUMBER(); 

                }
                break;
            case 125 :
                // InternalAsmetaL.g:1:820: RULE_REAL_NUMBER
                {
                mRULE_REAL_NUMBER(); 

                }
                break;
            case 126 :
                // InternalAsmetaL.g:1:837: RULE_ENUM_ID
                {
                mRULE_ENUM_ID(); 

                }
                break;
            case 127 :
                // InternalAsmetaL.g:1:850: RULE_RULE_ID
                {
                mRULE_RULE_ID(); 

                }
                break;
            case 128 :
                // InternalAsmetaL.g:1:863: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 129 :
                // InternalAsmetaL.g:1:871: RULE_CHAR_LITERAL
                {
                mRULE_CHAR_LITERAL(); 

                }
                break;
            case 130 :
                // InternalAsmetaL.g:1:889: RULE_STRING_LITERAL
                {
                mRULE_STRING_LITERAL(); 

                }
                break;
            case 131 :
                // InternalAsmetaL.g:1:909: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 132 :
                // InternalAsmetaL.g:1:921: RULE_COMPLEX_NUMBER
                {
                mRULE_COMPLEX_NUMBER(); 

                }
                break;
            case 133 :
                // InternalAsmetaL.g:1:941: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 134 :
                // InternalAsmetaL.g:1:957: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 135 :
                // InternalAsmetaL.g:1:973: RULE_WS
                {
                mRULE_WS(); 

                }
                break;

        }

    }


    protected DFA18 dfa18 = new DFA18(this);
    static final String DFA18_eotS =
        "\1\uffff\4\65\3\uffff\1\65\1\uffff\1\65\1\122\1\uffff\17\65\3\uffff\2\65\1\166\5\65\1\uffff\1\u0082\1\u0086\1\uffff\1\u0089\1\u008a\1\u008d\1\65\2\uffff\1\65\1\uffff\1\u0090\1\65\4\uffff\7\65\1\u00a9\2\65\1\u00ae\1\u00b1\14\65\2\uffff\4\65\1\u00c5\1\65\1\u00c9\5\65\1\u00cf\1\65\2\u00c5\2\65\3\u00c5\15\65\2\uffff\11\65\16\uffff\2\65\14\uffff\1\65\1\u00f4\2\65\1\u00f7\1\65\1\u00fa\6\65\1\uffff\4\65\1\uffff\1\u0107\1\65\1\uffff\1\65\1\uffff\14\65\1\u0119\1\65\1\u011c\2\65\1\uffff\1\u00c5\1\65\1\u00c9\1\uffff\4\65\1\u0124\1\uffff\1\65\1\u0127\1\u00c5\2\65\1\u012c\2\u00c5\6\65\1\u0135\2\65\1\u0138\3\65\1\u013c\1\65\1\u013e\3\65\1\u0142\1\u0143\4\65\1\u0148\1\uffff\1\65\1\uffff\2\65\1\uffff\2\65\1\uffff\1\65\1\u014f\10\65\1\u0158\1\65\1\uffff\5\65\1\u015f\4\65\1\u0166\5\65\1\u016c\1\uffff\2\65\1\uffff\2\65\1\u0171\1\65\1\u0173\1\u0174\1\u0175\1\uffff\1\65\1\u00c5\1\uffff\1\u00c5\1\u0179\1\65\1\u00c5\1\uffff\2\u00c5\2\65\1\u0180\1\65\1\u0182\1\65\1\uffff\2\65\1\uffff\1\65\1\u0187\1\65\1\uffff\1\65\1\uffff\1\65\1\u018b\1\65\2\uffff\1\65\1\u018e\2\65\1\uffff\1\65\1\u0192\4\65\1\uffff\1\u0197\7\65\1\uffff\4\65\1\u01a3\1\65\1\uffff\1\u01a5\5\65\1\uffff\5\65\1\uffff\2\65\1\u01b2\1\u01b3\1\uffff\1\u01b4\3\uffff\1\65\2\u00c5\1\uffff\1\65\2\u00c5\1\u01bb\1\65\1\u01bd\1\uffff\1\65\1\uffff\3\65\1\u01c2\1\uffff\1\65\1\u01c4\1\65\1\uffff\1\65\1\u01c8\1\uffff\1\u01c9\1\65\1\u01cb\1\uffff\2\65\1\u01ce\1\65\1\uffff\3\65\1\u01d3\1\65\1\u01d5\2\65\1\u01d8\1\65\1\u01da\1\uffff\1\u01db\1\uffff\2\65\1\u01de\1\u01df\1\u01e0\2\65\1\u01e3\1\u01e4\1\u01e5\1\65\1\u01e7\3\uffff\1\65\2\u00c5\1\65\2\u00c5\1\uffff\1\65\1\uffff\1\65\1\u01f0\2\65\1\uffff\1\65\1\uffff\1\65\1\u01f5\1\65\2\uffff\1\u01f7\1\uffff\2\65\1\uffff\1\65\1\u01fb\1\65\1\u01fd\1\uffff\1\u01fe\1\uffff\1\u01ff\1\65\1\uffff\1\u0201\2\uffff\2\65\3\uffff\2\65\3\uffff\1\65\1\uffff\1\65\1\u0208\1\u00c5\1\u020a\1\u020b\1\u020c\1\u020d\1\u020e\1\uffff\1\u020f\1\u0210\2\65\1\uffff\1\65\1\uffff\1\65\1\u0215\1\65\1\uffff\1\65\3\uffff\1\65\1\uffff\3\65\1\u021c\1\u021d\1\65\1\uffff\1\u00c5\7\uffff\1\u0220\1\65\1\u0222\1\u0223\1\uffff\1\u0224\1\65\1\u0226\1\65\1\u0228\1\u0229\2\uffff\1\u022a\1\u00c5\1\uffff\1\65\3\uffff\1\65\1\uffff\1\u022e\3\uffff\1\u022f\1\u0230\1\u0231\4\uffff";
    static final String DFA18_eofS =
        "\u0232\uffff";
    static final String DFA18_minS =
        "\1\11\1\142\1\141\1\145\1\60\3\uffff\1\154\1\uffff\1\145\1\75\1\uffff\1\141\1\101\1\137\1\150\1\162\4\101\1\141\5\101\3\uffff\2\101\1\76\1\145\1\141\2\157\1\150\1\uffff\1\75\1\55\1\uffff\1\52\1\101\1\56\1\156\2\uffff\1\141\1\uffff\1\56\1\101\2\uffff\1\0\1\uffff\1\155\1\145\1\144\1\163\1\144\1\143\1\146\1\60\1\156\1\160\2\60\1\145\1\56\1\151\1\144\1\163\1\147\1\142\2\141\2\151\1\161\2\uffff\1\156\1\162\1\154\1\145\1\60\1\154\1\60\1\162\1\165\2\145\1\164\1\60\1\150\2\60\1\141\1\155\3\60\1\164\1\163\1\141\1\154\1\162\1\161\1\164\1\157\1\147\1\144\1\157\1\167\1\160\2\uffff\1\143\1\164\1\156\1\163\1\157\1\164\1\162\1\151\1\164\16\uffff\1\144\1\162\3\uffff\7\0\2\uffff\1\156\1\60\1\156\1\144\1\60\1\164\1\60\1\151\1\156\1\162\1\141\1\151\1\141\1\uffff\1\141\1\154\1\164\1\141\1\uffff\1\60\1\157\1\uffff\1\162\1\uffff\1\157\1\163\1\145\1\155\1\151\1\145\1\156\1\163\1\164\1\162\1\164\1\160\1\60\1\143\1\60\1\163\1\156\1\uffff\1\60\1\145\1\60\1\uffff\1\142\1\145\1\156\1\162\1\60\1\uffff\1\145\2\60\1\162\1\160\3\60\1\145\1\151\1\154\2\145\1\151\1\60\1\165\1\154\1\60\1\145\1\144\1\145\1\60\1\141\1\60\1\164\1\145\1\157\2\60\1\154\1\150\1\145\1\161\1\60\1\uffff\1\143\1\uffff\1\164\1\157\1\uffff\1\162\1\154\1\uffff\1\164\1\60\1\157\1\165\1\156\1\166\1\151\1\155\1\162\1\151\1\60\1\162\1\uffff\1\156\1\141\1\162\1\164\1\156\1\60\1\146\2\145\1\141\1\60\1\141\1\145\1\151\1\145\1\143\1\60\1\uffff\1\164\1\154\1\uffff\1\145\1\164\1\60\1\157\3\60\1\uffff\1\162\1\60\1\uffff\2\60\1\154\1\60\1\uffff\2\60\1\147\1\143\1\60\1\162\1\60\1\156\1\uffff\1\162\1\145\1\uffff\1\146\1\60\1\162\1\uffff\1\154\1\uffff\1\162\1\60\1\163\2\uffff\1\145\1\60\1\146\1\165\1\uffff\1\162\1\60\1\155\1\141\1\145\1\157\1\uffff\1\60\1\154\1\151\1\145\1\156\1\151\1\164\1\145\1\uffff\1\151\1\145\2\164\1\60\1\144\1\uffff\1\60\1\145\1\151\1\161\1\164\1\162\1\uffff\2\164\1\143\1\144\1\150\1\uffff\1\151\1\154\2\60\1\uffff\1\60\3\uffff\1\167\2\60\1\uffff\1\145\3\60\1\145\1\60\1\uffff\1\166\1\uffff\1\147\2\141\1\60\1\uffff\1\163\1\60\1\157\1\uffff\1\145\1\60\1\uffff\1\60\1\145\1\60\1\uffff\1\141\1\143\1\60\1\162\1\uffff\2\164\1\144\1\60\1\143\1\60\1\163\1\141\1\60\1\145\1\60\1\uffff\1\60\1\uffff\1\162\1\164\3\60\1\165\1\157\3\60\1\157\1\60\3\uffff\1\151\2\60\1\170\2\60\1\uffff\1\162\1\uffff\1\145\1\60\1\154\1\156\1\uffff\1\145\1\uffff\1\154\1\60\1\145\2\uffff\1\60\1\uffff\1\151\1\164\1\uffff\1\145\1\60\1\151\1\60\1\uffff\1\60\1\uffff\1\60\1\156\1\uffff\1\60\2\uffff\1\141\1\143\3\uffff\1\162\1\146\3\uffff\1\156\1\uffff\1\163\7\60\1\uffff\2\60\1\164\1\154\1\uffff\1\143\1\uffff\1\156\1\60\1\144\1\uffff\1\157\3\uffff\1\164\1\uffff\1\164\1\150\1\145\2\60\1\145\1\uffff\1\60\7\uffff\1\60\1\145\2\60\1\uffff\1\60\1\156\1\60\1\145\2\60\2\uffff\2\60\1\uffff\1\144\3\uffff\1\163\1\uffff\1\60\3\uffff\3\60\4\uffff";
    static final String DFA18_maxS =
        "\1\175\1\163\1\157\1\171\1\164\3\uffff\1\170\1\uffff\1\167\1\75\1\uffff\1\165\1\147\2\165\1\166\1\157\2\132\1\156\1\141\1\165\1\164\1\141\1\157\1\156\3\uffff\1\162\1\141\1\76\4\157\1\151\1\uffff\1\76\1\75\1\uffff\1\57\1\172\1\57\1\156\2\uffff\1\141\1\uffff\1\156\1\132\2\uffff\1\uffff\1\uffff\1\171\1\145\1\171\1\163\1\156\1\151\1\162\1\172\1\156\1\160\2\172\1\145\1\71\1\164\1\165\1\163\1\147\1\142\2\141\2\151\1\161\2\uffff\1\156\1\162\1\154\1\145\1\172\1\154\1\172\1\162\1\165\2\145\1\164\1\172\1\150\2\172\1\141\1\155\3\172\1\164\2\163\1\154\1\162\1\161\1\164\1\157\1\147\1\144\1\157\1\167\1\160\2\uffff\1\143\1\164\1\156\1\163\1\157\1\164\1\162\1\151\1\164\16\uffff\1\151\1\162\3\uffff\7\uffff\2\uffff\1\156\1\172\1\156\1\144\1\172\1\164\1\172\1\151\1\156\1\162\2\151\1\141\1\uffff\1\141\1\157\1\164\1\141\1\uffff\1\172\1\157\1\uffff\1\162\1\uffff\1\157\1\163\1\145\1\155\1\163\1\145\1\156\1\163\1\164\1\162\1\164\1\160\1\172\1\143\1\172\1\163\1\156\1\uffff\1\172\1\145\1\172\1\uffff\1\142\1\145\1\156\1\162\1\172\1\uffff\1\145\2\172\1\162\1\160\3\172\1\145\1\151\1\154\2\145\1\151\1\172\1\165\1\154\1\172\1\145\1\144\1\145\1\172\1\141\1\172\1\164\1\145\1\157\2\172\1\154\1\150\1\145\1\161\1\172\1\uffff\1\143\1\uffff\1\164\1\157\1\uffff\1\162\1\154\1\uffff\1\164\1\172\1\157\1\165\1\156\1\166\1\151\1\155\1\162\1\151\1\172\1\162\1\uffff\1\156\1\141\1\162\1\164\1\156\1\172\1\164\1\167\1\145\1\141\1\172\1\141\1\145\1\151\1\145\1\143\1\172\1\uffff\1\164\1\154\1\uffff\1\145\1\164\1\172\1\157\3\172\1\uffff\1\162\1\172\1\uffff\2\172\1\154\1\172\1\uffff\2\172\1\147\1\143\1\172\1\162\1\172\1\156\1\uffff\1\162\1\145\1\uffff\1\146\1\172\1\162\1\uffff\1\154\1\uffff\1\162\1\172\1\163\2\uffff\1\145\1\172\1\146\1\165\1\uffff\1\162\1\172\1\155\1\141\1\145\1\157\1\uffff\1\172\1\154\1\151\1\145\1\156\1\151\1\164\1\145\1\uffff\1\151\1\145\2\164\1\172\1\144\1\uffff\1\172\1\145\1\151\1\161\1\164\1\162\1\uffff\2\164\1\143\1\144\1\150\1\uffff\1\151\1\154\2\172\1\uffff\1\172\3\uffff\1\167\2\172\1\uffff\1\145\3\172\1\145\1\172\1\uffff\1\166\1\uffff\1\147\2\141\1\172\1\uffff\1\163\1\172\1\157\1\uffff\1\145\1\172\1\uffff\1\172\1\145\1\172\1\uffff\1\141\1\143\1\172\1\162\1\uffff\2\164\1\144\1\172\1\143\1\172\1\163\1\141\1\172\1\145\1\172\1\uffff\1\172\1\uffff\1\162\1\164\3\172\1\165\1\157\3\172\1\157\1\172\3\uffff\1\151\2\172\1\170\2\172\1\uffff\1\162\1\uffff\1\145\1\172\1\154\1\156\1\uffff\1\145\1\uffff\1\154\1\172\1\145\2\uffff\1\172\1\uffff\1\151\1\164\1\uffff\1\145\1\172\1\151\1\172\1\uffff\1\172\1\uffff\1\172\1\156\1\uffff\1\172\2\uffff\1\141\1\143\3\uffff\1\162\1\146\3\uffff\1\156\1\uffff\1\163\7\172\1\uffff\2\172\1\164\1\154\1\uffff\1\143\1\uffff\1\156\1\172\1\144\1\uffff\1\157\3\uffff\1\164\1\uffff\1\164\1\150\1\145\2\172\1\145\1\uffff\1\172\7\uffff\1\172\1\145\2\172\1\uffff\1\172\1\156\1\172\1\145\2\172\2\uffff\2\172\1\uffff\1\144\3\uffff\1\163\1\uffff\1\172\3\uffff\3\172\4\uffff";
    static final String DFA18_acceptS =
        "\5\uffff\1\7\1\10\1\11\1\uffff\1\13\2\uffff\1\20\17\uffff\1\61\1\62\1\63\10\uffff\1\113\2\uffff\1\120\4\uffff\1\140\1\141\1\uffff\1\172\2\uffff\1\u0080\1\u0081\1\uffff\1\u0087\30\uffff\1\153\1\15\42\uffff\1\73\1\121\11\uffff\1\116\1\151\1\114\1\117\1\150\1\166\1\115\1\u0085\1\u0086\1\122\1\123\1\170\1\171\1\124\2\uffff\1\173\1\174\1\175\7\uffff\1\u0082\1\u0083\15\uffff\1\157\4\uffff\1\22\2\uffff\1\130\1\uffff\1\u0084\21\uffff\1\176\3\uffff\1\177\5\uffff\1\103\42\uffff\1\u0082\1\uffff\1\2\2\uffff\1\102\2\uffff\1\106\14\uffff\1\107\21\uffff\1\162\2\uffff\1\112\7\uffff\1\101\2\uffff\1\33\4\uffff\1\35\10\uffff\1\66\2\uffff\1\70\3\uffff\1\71\1\uffff\1\146\3\uffff\1\104\1\105\4\uffff\1\154\6\uffff\1\4\10\uffff\1\16\6\uffff\1\60\6\uffff\1\132\5\uffff\1\152\4\uffff\1\27\1\uffff\1\125\1\131\1\32\3\uffff\1\52\6\uffff\1\47\1\uffff\1\64\4\uffff\1\65\3\uffff\1\135\2\uffff\1\144\3\uffff\1\23\4\uffff\1\26\13\uffff\1\142\1\uffff\1\133\14\uffff\1\126\1\24\1\30\6\uffff\1\41\1\uffff\1\45\4\uffff\1\55\1\uffff\1\75\3\uffff\1\111\1\127\1\uffff\1\1\2\uffff\1\3\4\uffff\1\17\1\uffff\1\6\2\uffff\1\160\1\uffff\1\12\1\161\2\uffff\1\163\1\147\1\155\2\uffff\1\74\1\77\1\134\1\uffff\1\145\10\uffff\1\50\4\uffff\1\156\1\uffff\1\143\3\uffff\1\5\1\uffff\1\72\1\42\1\110\1\uffff\1\164\6\uffff\1\34\1\uffff\1\53\1\36\1\37\1\46\1\57\1\51\1\54\4\uffff\1\56\6\uffff\1\43\1\21\2\uffff\1\67\1\uffff\1\167\1\44\1\100\1\uffff\1\31\1\uffff\1\137\1\14\1\136\3\uffff\1\165\1\40\1\76\1\25";
    static final String DFA18_specialS =
        "\67\uffff\1\5\133\uffff\1\1\1\2\1\0\1\7\1\6\1\4\1\3\u0198\uffff}>";
    static final String[] DFA18_transitionS = {
            "\2\70\2\uffff\1\70\22\uffff\1\70\1\47\1\67\1\uffff\1\62\2\uffff\1\66\1\5\1\7\1\11\1\52\1\6\1\41\1\55\1\53\12\63\1\13\1\uffff\1\51\1\14\1\50\2\uffff\1\16\1\32\1\22\5\64\1\25\1\24\1\64\1\23\1\40\1\31\1\64\1\37\1\64\1\27\1\30\1\64\1\33\5\64\1\57\1\uffff\1\60\1\54\1\65\1\uffff\1\1\1\26\1\43\1\3\1\10\1\15\2\65\1\4\2\65\1\42\1\2\1\44\1\21\1\61\1\65\1\17\1\12\1\20\1\56\1\65\1\46\1\45\2\65\1\34\1\35\1\36",
            "\1\74\4\uffff\1\72\6\uffff\1\73\4\uffff\1\71",
            "\1\76\15\uffff\1\75",
            "\1\77\11\uffff\1\100\11\uffff\1\101",
            "\12\106\54\uffff\1\104\6\uffff\1\102\1\103\5\uffff\1\105",
            "",
            "",
            "",
            "\1\111\1\uffff\1\110\11\uffff\1\107",
            "",
            "\1\120\2\uffff\1\115\1\112\1\uffff\1\117\10\uffff\1\114\1\113\1\uffff\1\116",
            "\1\121",
            "",
            "\1\125\15\uffff\1\124\5\uffff\1\123",
            "\32\127\14\uffff\1\126",
            "\1\131\25\uffff\1\130",
            "\1\134\11\uffff\1\133\2\uffff\1\132",
            "\1\137\1\uffff\1\140\1\136\1\135",
            "\16\127\1\142\4\127\1\141\6\127\15\uffff\1\143\6\uffff\1\144",
            "\23\127\1\145\6\127",
            "\24\127\1\146\5\127",
            "\15\127\1\147\14\127\23\uffff\1\150",
            "\1\151",
            "\32\127\12\uffff\1\152\17\uffff\1\153",
            "\32\127\12\uffff\1\155\16\uffff\1\154",
            "\32\127\6\uffff\1\156",
            "\32\127\6\uffff\1\160\15\uffff\1\157",
            "\32\127\23\uffff\1\161",
            "",
            "",
            "",
            "\32\127\24\uffff\1\163\2\uffff\1\162",
            "\32\127\6\uffff\1\164",
            "\1\165",
            "\1\170\11\uffff\1\167",
            "\1\172\6\uffff\1\173\6\uffff\1\171",
            "\1\174",
            "\1\175",
            "\1\176\1\177",
            "",
            "\1\u0080\1\u0081",
            "\1\u0085\16\uffff\1\u0084\1\u0083",
            "",
            "\1\u0087\4\uffff\1\u0088",
            "\32\64\4\uffff\1\65\1\uffff\32\65",
            "\1\u008b\1\u008c",
            "\1\u008e",
            "",
            "",
            "\1\u008f",
            "",
            "\1\u0092\1\uffff\12\63\64\uffff\1\u0091",
            "\32\127",
            "",
            "",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "",
            "\1\u009d\13\uffff\1\u009c",
            "\1\u009e",
            "\1\u00a0\24\uffff\1\u009f",
            "\1\u00a1",
            "\1\u00a2\11\uffff\1\u00a3",
            "\1\u00a5\5\uffff\1\u00a4",
            "\1\u00a6\13\uffff\1\u00a7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\14\65\1\u00a8\15\65",
            "\1\u00aa",
            "\1\u00ab",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\10\65\1\u00ac\14\65\1\u00ad\4\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\5\65\1\u00af\7\65\1\u00b0\14\65",
            "\1\u00b2",
            "\1\u00b3\1\uffff\12\106",
            "\1\u00b5\6\uffff\1\u00b4\3\uffff\1\u00b6",
            "\1\u00b8\20\uffff\1\u00b7",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "",
            "",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "\1\u00c4",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u00c7",
            "\12\u00c8\7\uffff\32\u00c8\4\uffff\1\u00c8\1\uffff\32\u00c8",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\1\u00ce",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00d0",
            "\12\u00c6\7\uffff\13\u00c6\1\u00d1\16\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\14\u00c6\1\u00d2\15\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u00d3",
            "\1\u00d4",
            "\12\u00c6\7\uffff\13\u00c6\1\u00d5\16\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\22\u00c6\1\u00d6\7\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\25\u00c6\1\u00d7\4\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da\21\uffff\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\1\u00e4",
            "\1\u00e5",
            "",
            "",
            "\1\u00e6",
            "\1\u00e7",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00ef\4\uffff\1\u00f0",
            "\1\u00f1",
            "",
            "",
            "",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "\11\u009b\2\u0096\2\u009b\1\u0096\22\u009b\1\u0096\1\u0098\1\u009a\1\u009b\2\u0098\1\u009b\1\u0097\2\u0098\1\u009b\5\u0098\12\u0095\7\u0098\32\u0093\5\u0098\1\u009b\32\u0094\1\u009b\1\u0098\53\u009b\1\u0098\32\u009b\1\u0099\34\u009b\1\u0099\7\u009b\2\u0099\2\u009b\1\u0099\5\u009b\1\u0099\6\u009b\1\u0099\uff06\u009b",
            "",
            "",
            "\1\u00f3",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00f5",
            "\1\u00f6",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00f8",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\24\65\1\u00f9\5\65",
            "\1\u00fb",
            "\1\u00fc",
            "\1\u00fd",
            "\1\u00fe\7\uffff\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "",
            "\1\u0102",
            "\1\u0104\2\uffff\1\u0103",
            "\1\u0105",
            "\1\u0106",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0108",
            "",
            "\1\u0109",
            "",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "\1\u010d",
            "\1\u010e\2\uffff\1\u0110\3\uffff\1\u0111\2\uffff\1\u010f",
            "\1\u0112",
            "\1\u0113",
            "\1\u0114",
            "\1\u0115",
            "\1\u0116",
            "\1\u0117",
            "\1\u0118",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u011a",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\1\u011b\31\65",
            "\1\u011d",
            "\1\u011e",
            "",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u011f",
            "\12\u00c8\7\uffff\32\u00c8\4\uffff\1\u00c8\1\uffff\32\u00c8",
            "",
            "\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0125",
            "\12\u00c6\7\uffff\22\u00c6\1\u0126\7\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\17\u00c6\1\u0128\12\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u0129",
            "\1\u012a",
            "\12\u00c6\7\uffff\22\u00c6\1\u012b\7\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\23\u00c6\1\u012d\6\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\1\u012e\31\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u012f",
            "\1\u0130",
            "\1\u0131",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0136",
            "\1\u0137",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0139",
            "\1\u013a",
            "\1\u013b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u013d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u013f",
            "\1\u0140",
            "\1\u0141",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0144",
            "\1\u0145",
            "\1\u0146",
            "\1\u0147",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0149",
            "",
            "\1\u014a",
            "\1\u014b",
            "",
            "\1\u014c",
            "\1\u014d",
            "",
            "\1\u014e",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0150",
            "\1\u0151",
            "\1\u0152",
            "\1\u0153",
            "\1\u0154",
            "\1\u0155",
            "\1\u0156",
            "\1\u0157",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0159",
            "",
            "\1\u015a",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0160\15\uffff\1\u0161",
            "\1\u0163\21\uffff\1\u0162",
            "\1\u0164",
            "\1\u0165",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0167",
            "\1\u0168",
            "\1\u0169",
            "\1\u016a",
            "\1\u016b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u016d",
            "\1\u016e",
            "",
            "\1\u016f",
            "\1\u0170",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0172",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0176",
            "\12\u00c6\7\uffff\17\u00c6\1\u0177\12\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "\12\u00c6\7\uffff\1\u0178\31\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u017a",
            "\12\u00c6\7\uffff\17\u00c6\1\u017b\12\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "\12\u00c6\7\uffff\10\u00c6\1\u017c\21\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\21\u00c6\1\u017d\10\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u017e",
            "\1\u017f",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0181",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0183",
            "",
            "\1\u0184",
            "\1\u0185",
            "",
            "\1\u0186",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0188",
            "",
            "\1\u0189",
            "",
            "\1\u018a",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u018c",
            "",
            "",
            "\1\u018d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u018f",
            "\1\u0190",
            "",
            "\1\u0191",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0193",
            "\1\u0194",
            "\1\u0195",
            "\1\u0196",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0198",
            "\1\u0199",
            "\1\u019a",
            "\1\u019b",
            "\1\u019c",
            "\1\u019d",
            "\1\u019e",
            "",
            "\1\u019f",
            "\1\u01a0",
            "\1\u01a1",
            "\1\u01a2",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01a4",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8",
            "\1\u01a9",
            "\1\u01aa",
            "",
            "\1\u01ab",
            "\1\u01ac",
            "\1\u01ad",
            "\1\u01ae",
            "\1\u01af",
            "",
            "\1\u01b0",
            "\1\u01b1",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\1\u01b5",
            "\12\u00c6\7\uffff\4\u00c6\1\u01b6\25\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\22\u00c6\1\u01b7\7\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "\1\u01b8",
            "\12\u00c6\7\uffff\4\u00c6\1\u01b9\25\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\2\u00c6\1\u01ba\27\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u01bc",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01be",
            "",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01c3",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01c5",
            "",
            "\1\u01c6",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\21\65\1\u01c7\10\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01ca",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01cc",
            "\1\u01cd",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01cf",
            "",
            "\1\u01d0",
            "\1\u01d1",
            "\1\u01d2",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01d4",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01d6",
            "\1\u01d7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01d9",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01dc",
            "\1\u01dd",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01e1",
            "\1\u01e2",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01e6",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\1\u01e8",
            "\12\u00c6\7\uffff\2\u00c6\1\u01e9\27\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\22\u00c6\1\u01ea\7\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\1\u01eb",
            "\12\u00c6\7\uffff\2\u00c6\1\u01ec\27\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\4\u00c6\1\u01ed\25\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "\1\u01ee",
            "",
            "\1\u01ef",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01f1",
            "\1\u01f2",
            "",
            "\1\u01f3",
            "",
            "\1\u01f4",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01f6",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01f8",
            "\1\u01f9",
            "",
            "\1\u01fa",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01fc",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0200",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u0202",
            "\1\u0203",
            "",
            "",
            "",
            "\1\u0204",
            "\1\u0205",
            "",
            "",
            "",
            "\1\u0206",
            "",
            "\1\u0207",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\10\u00c6\1\u0209\21\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0211",
            "\1\u0212",
            "",
            "\1\u0213",
            "",
            "\1\u0214",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0216",
            "",
            "\1\u0217",
            "",
            "",
            "",
            "\1\u0218",
            "",
            "\1\u0219",
            "\1\u021a",
            "\1\u021b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u021e",
            "",
            "\12\u00c6\7\uffff\16\u00c6\1\u021f\13\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0221",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0225",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0227",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\u00c6\7\uffff\15\u00c6\1\u022b\14\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "",
            "\1\u022c",
            "",
            "",
            "",
            "\1\u022d",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\12\u00c6\7\uffff\32\u00c6\4\uffff\1\u00c6\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | T__139 | T__140 | T__141 | T__142 | T__143 | T__144 | RULE_NUMBER_TOKEN | RULE_NATNUMBER | RULE_REAL_NUMBER | RULE_ENUM_ID | RULE_RULE_ID | RULE_ID | RULE_CHAR_LITERAL | RULE_STRING_LITERAL | RULE_STRING | RULE_COMPLEX_NUMBER | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_149 = input.LA(1);

                        s = -1;
                        if ( (LA18_149=='\"') ) {s = 154;}

                        else if ( ((LA18_149>='A' && LA18_149<='Z')) ) {s = 147;}

                        else if ( ((LA18_149>='a' && LA18_149<='z')) ) {s = 148;}

                        else if ( ((LA18_149>='0' && LA18_149<='9')) ) {s = 149;}

                        else if ( ((LA18_149>='\t' && LA18_149<='\n')||LA18_149=='\r'||LA18_149==' ') ) {s = 150;}

                        else if ( (LA18_149=='\'') ) {s = 151;}

                        else if ( (LA18_149=='!'||(LA18_149>='$' && LA18_149<='%')||(LA18_149>='(' && LA18_149<=')')||(LA18_149>='+' && LA18_149<='/')||(LA18_149>=':' && LA18_149<='@')||(LA18_149>='[' && LA18_149<='_')||LA18_149=='|'||LA18_149=='\u00A8') ) {s = 152;}

                        else if ( (LA18_149=='\u00C3'||LA18_149=='\u00E0'||(LA18_149>='\u00E8' && LA18_149<='\u00E9')||LA18_149=='\u00EC'||LA18_149=='\u00F2'||LA18_149=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_149>='\u0000' && LA18_149<='\b')||(LA18_149>='\u000B' && LA18_149<='\f')||(LA18_149>='\u000E' && LA18_149<='\u001F')||LA18_149=='#'||LA18_149=='&'||LA18_149=='*'||LA18_149=='`'||LA18_149=='{'||(LA18_149>='}' && LA18_149<='\u00A7')||(LA18_149>='\u00A9' && LA18_149<='\u00C2')||(LA18_149>='\u00C4' && LA18_149<='\u00DF')||(LA18_149>='\u00E1' && LA18_149<='\u00E7')||(LA18_149>='\u00EA' && LA18_149<='\u00EB')||(LA18_149>='\u00ED' && LA18_149<='\u00F1')||(LA18_149>='\u00F3' && LA18_149<='\u00F8')||(LA18_149>='\u00FA' && LA18_149<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA18_147 = input.LA(1);

                        s = -1;
                        if ( (LA18_147=='\"') ) {s = 154;}

                        else if ( ((LA18_147>='A' && LA18_147<='Z')) ) {s = 147;}

                        else if ( ((LA18_147>='a' && LA18_147<='z')) ) {s = 148;}

                        else if ( ((LA18_147>='0' && LA18_147<='9')) ) {s = 149;}

                        else if ( ((LA18_147>='\t' && LA18_147<='\n')||LA18_147=='\r'||LA18_147==' ') ) {s = 150;}

                        else if ( (LA18_147=='\'') ) {s = 151;}

                        else if ( (LA18_147=='!'||(LA18_147>='$' && LA18_147<='%')||(LA18_147>='(' && LA18_147<=')')||(LA18_147>='+' && LA18_147<='/')||(LA18_147>=':' && LA18_147<='@')||(LA18_147>='[' && LA18_147<='_')||LA18_147=='|'||LA18_147=='\u00A8') ) {s = 152;}

                        else if ( (LA18_147=='\u00C3'||LA18_147=='\u00E0'||(LA18_147>='\u00E8' && LA18_147<='\u00E9')||LA18_147=='\u00EC'||LA18_147=='\u00F2'||LA18_147=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_147>='\u0000' && LA18_147<='\b')||(LA18_147>='\u000B' && LA18_147<='\f')||(LA18_147>='\u000E' && LA18_147<='\u001F')||LA18_147=='#'||LA18_147=='&'||LA18_147=='*'||LA18_147=='`'||LA18_147=='{'||(LA18_147>='}' && LA18_147<='\u00A7')||(LA18_147>='\u00A9' && LA18_147<='\u00C2')||(LA18_147>='\u00C4' && LA18_147<='\u00DF')||(LA18_147>='\u00E1' && LA18_147<='\u00E7')||(LA18_147>='\u00EA' && LA18_147<='\u00EB')||(LA18_147>='\u00ED' && LA18_147<='\u00F1')||(LA18_147>='\u00F3' && LA18_147<='\u00F8')||(LA18_147>='\u00FA' && LA18_147<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA18_148 = input.LA(1);

                        s = -1;
                        if ( (LA18_148=='\"') ) {s = 154;}

                        else if ( ((LA18_148>='A' && LA18_148<='Z')) ) {s = 147;}

                        else if ( ((LA18_148>='a' && LA18_148<='z')) ) {s = 148;}

                        else if ( ((LA18_148>='0' && LA18_148<='9')) ) {s = 149;}

                        else if ( ((LA18_148>='\t' && LA18_148<='\n')||LA18_148=='\r'||LA18_148==' ') ) {s = 150;}

                        else if ( (LA18_148=='\'') ) {s = 151;}

                        else if ( (LA18_148=='!'||(LA18_148>='$' && LA18_148<='%')||(LA18_148>='(' && LA18_148<=')')||(LA18_148>='+' && LA18_148<='/')||(LA18_148>=':' && LA18_148<='@')||(LA18_148>='[' && LA18_148<='_')||LA18_148=='|'||LA18_148=='\u00A8') ) {s = 152;}

                        else if ( (LA18_148=='\u00C3'||LA18_148=='\u00E0'||(LA18_148>='\u00E8' && LA18_148<='\u00E9')||LA18_148=='\u00EC'||LA18_148=='\u00F2'||LA18_148=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_148>='\u0000' && LA18_148<='\b')||(LA18_148>='\u000B' && LA18_148<='\f')||(LA18_148>='\u000E' && LA18_148<='\u001F')||LA18_148=='#'||LA18_148=='&'||LA18_148=='*'||LA18_148=='`'||LA18_148=='{'||(LA18_148>='}' && LA18_148<='\u00A7')||(LA18_148>='\u00A9' && LA18_148<='\u00C2')||(LA18_148>='\u00C4' && LA18_148<='\u00DF')||(LA18_148>='\u00E1' && LA18_148<='\u00E7')||(LA18_148>='\u00EA' && LA18_148<='\u00EB')||(LA18_148>='\u00ED' && LA18_148<='\u00F1')||(LA18_148>='\u00F3' && LA18_148<='\u00F8')||(LA18_148>='\u00FA' && LA18_148<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA18_153 = input.LA(1);

                        s = -1;
                        if ( (LA18_153=='\"') ) {s = 154;}

                        else if ( ((LA18_153>='A' && LA18_153<='Z')) ) {s = 147;}

                        else if ( ((LA18_153>='a' && LA18_153<='z')) ) {s = 148;}

                        else if ( ((LA18_153>='0' && LA18_153<='9')) ) {s = 149;}

                        else if ( ((LA18_153>='\t' && LA18_153<='\n')||LA18_153=='\r'||LA18_153==' ') ) {s = 150;}

                        else if ( (LA18_153=='\'') ) {s = 151;}

                        else if ( (LA18_153=='!'||(LA18_153>='$' && LA18_153<='%')||(LA18_153>='(' && LA18_153<=')')||(LA18_153>='+' && LA18_153<='/')||(LA18_153>=':' && LA18_153<='@')||(LA18_153>='[' && LA18_153<='_')||LA18_153=='|'||LA18_153=='\u00A8') ) {s = 152;}

                        else if ( (LA18_153=='\u00C3'||LA18_153=='\u00E0'||(LA18_153>='\u00E8' && LA18_153<='\u00E9')||LA18_153=='\u00EC'||LA18_153=='\u00F2'||LA18_153=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_153>='\u0000' && LA18_153<='\b')||(LA18_153>='\u000B' && LA18_153<='\f')||(LA18_153>='\u000E' && LA18_153<='\u001F')||LA18_153=='#'||LA18_153=='&'||LA18_153=='*'||LA18_153=='`'||LA18_153=='{'||(LA18_153>='}' && LA18_153<='\u00A7')||(LA18_153>='\u00A9' && LA18_153<='\u00C2')||(LA18_153>='\u00C4' && LA18_153<='\u00DF')||(LA18_153>='\u00E1' && LA18_153<='\u00E7')||(LA18_153>='\u00EA' && LA18_153<='\u00EB')||(LA18_153>='\u00ED' && LA18_153<='\u00F1')||(LA18_153>='\u00F3' && LA18_153<='\u00F8')||(LA18_153>='\u00FA' && LA18_153<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA18_152 = input.LA(1);

                        s = -1;
                        if ( (LA18_152=='\"') ) {s = 154;}

                        else if ( ((LA18_152>='A' && LA18_152<='Z')) ) {s = 147;}

                        else if ( ((LA18_152>='a' && LA18_152<='z')) ) {s = 148;}

                        else if ( ((LA18_152>='0' && LA18_152<='9')) ) {s = 149;}

                        else if ( ((LA18_152>='\t' && LA18_152<='\n')||LA18_152=='\r'||LA18_152==' ') ) {s = 150;}

                        else if ( (LA18_152=='\'') ) {s = 151;}

                        else if ( (LA18_152=='!'||(LA18_152>='$' && LA18_152<='%')||(LA18_152>='(' && LA18_152<=')')||(LA18_152>='+' && LA18_152<='/')||(LA18_152>=':' && LA18_152<='@')||(LA18_152>='[' && LA18_152<='_')||LA18_152=='|'||LA18_152=='\u00A8') ) {s = 152;}

                        else if ( (LA18_152=='\u00C3'||LA18_152=='\u00E0'||(LA18_152>='\u00E8' && LA18_152<='\u00E9')||LA18_152=='\u00EC'||LA18_152=='\u00F2'||LA18_152=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_152>='\u0000' && LA18_152<='\b')||(LA18_152>='\u000B' && LA18_152<='\f')||(LA18_152>='\u000E' && LA18_152<='\u001F')||LA18_152=='#'||LA18_152=='&'||LA18_152=='*'||LA18_152=='`'||LA18_152=='{'||(LA18_152>='}' && LA18_152<='\u00A7')||(LA18_152>='\u00A9' && LA18_152<='\u00C2')||(LA18_152>='\u00C4' && LA18_152<='\u00DF')||(LA18_152>='\u00E1' && LA18_152<='\u00E7')||(LA18_152>='\u00EA' && LA18_152<='\u00EB')||(LA18_152>='\u00ED' && LA18_152<='\u00F1')||(LA18_152>='\u00F3' && LA18_152<='\u00F8')||(LA18_152>='\u00FA' && LA18_152<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA18_55 = input.LA(1);

                        s = -1;
                        if ( ((LA18_55>='A' && LA18_55<='Z')) ) {s = 147;}

                        else if ( ((LA18_55>='a' && LA18_55<='z')) ) {s = 148;}

                        else if ( ((LA18_55>='0' && LA18_55<='9')) ) {s = 149;}

                        else if ( ((LA18_55>='\t' && LA18_55<='\n')||LA18_55=='\r'||LA18_55==' ') ) {s = 150;}

                        else if ( (LA18_55=='\'') ) {s = 151;}

                        else if ( (LA18_55=='!'||(LA18_55>='$' && LA18_55<='%')||(LA18_55>='(' && LA18_55<=')')||(LA18_55>='+' && LA18_55<='/')||(LA18_55>=':' && LA18_55<='@')||(LA18_55>='[' && LA18_55<='_')||LA18_55=='|'||LA18_55=='\u00A8') ) {s = 152;}

                        else if ( (LA18_55=='\u00C3'||LA18_55=='\u00E0'||(LA18_55>='\u00E8' && LA18_55<='\u00E9')||LA18_55=='\u00EC'||LA18_55=='\u00F2'||LA18_55=='\u00F9') ) {s = 153;}

                        else if ( (LA18_55=='\"') ) {s = 154;}

                        else if ( ((LA18_55>='\u0000' && LA18_55<='\b')||(LA18_55>='\u000B' && LA18_55<='\f')||(LA18_55>='\u000E' && LA18_55<='\u001F')||LA18_55=='#'||LA18_55=='&'||LA18_55=='*'||LA18_55=='`'||LA18_55=='{'||(LA18_55>='}' && LA18_55<='\u00A7')||(LA18_55>='\u00A9' && LA18_55<='\u00C2')||(LA18_55>='\u00C4' && LA18_55<='\u00DF')||(LA18_55>='\u00E1' && LA18_55<='\u00E7')||(LA18_55>='\u00EA' && LA18_55<='\u00EB')||(LA18_55>='\u00ED' && LA18_55<='\u00F1')||(LA18_55>='\u00F3' && LA18_55<='\u00F8')||(LA18_55>='\u00FA' && LA18_55<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA18_151 = input.LA(1);

                        s = -1;
                        if ( (LA18_151=='\"') ) {s = 154;}

                        else if ( ((LA18_151>='A' && LA18_151<='Z')) ) {s = 147;}

                        else if ( ((LA18_151>='a' && LA18_151<='z')) ) {s = 148;}

                        else if ( ((LA18_151>='0' && LA18_151<='9')) ) {s = 149;}

                        else if ( ((LA18_151>='\t' && LA18_151<='\n')||LA18_151=='\r'||LA18_151==' ') ) {s = 150;}

                        else if ( (LA18_151=='\'') ) {s = 151;}

                        else if ( (LA18_151=='!'||(LA18_151>='$' && LA18_151<='%')||(LA18_151>='(' && LA18_151<=')')||(LA18_151>='+' && LA18_151<='/')||(LA18_151>=':' && LA18_151<='@')||(LA18_151>='[' && LA18_151<='_')||LA18_151=='|'||LA18_151=='\u00A8') ) {s = 152;}

                        else if ( (LA18_151=='\u00C3'||LA18_151=='\u00E0'||(LA18_151>='\u00E8' && LA18_151<='\u00E9')||LA18_151=='\u00EC'||LA18_151=='\u00F2'||LA18_151=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_151>='\u0000' && LA18_151<='\b')||(LA18_151>='\u000B' && LA18_151<='\f')||(LA18_151>='\u000E' && LA18_151<='\u001F')||LA18_151=='#'||LA18_151=='&'||LA18_151=='*'||LA18_151=='`'||LA18_151=='{'||(LA18_151>='}' && LA18_151<='\u00A7')||(LA18_151>='\u00A9' && LA18_151<='\u00C2')||(LA18_151>='\u00C4' && LA18_151<='\u00DF')||(LA18_151>='\u00E1' && LA18_151<='\u00E7')||(LA18_151>='\u00EA' && LA18_151<='\u00EB')||(LA18_151>='\u00ED' && LA18_151<='\u00F1')||(LA18_151>='\u00F3' && LA18_151<='\u00F8')||(LA18_151>='\u00FA' && LA18_151<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA18_150 = input.LA(1);

                        s = -1;
                        if ( (LA18_150=='\"') ) {s = 154;}

                        else if ( ((LA18_150>='A' && LA18_150<='Z')) ) {s = 147;}

                        else if ( ((LA18_150>='a' && LA18_150<='z')) ) {s = 148;}

                        else if ( ((LA18_150>='0' && LA18_150<='9')) ) {s = 149;}

                        else if ( ((LA18_150>='\t' && LA18_150<='\n')||LA18_150=='\r'||LA18_150==' ') ) {s = 150;}

                        else if ( (LA18_150=='\'') ) {s = 151;}

                        else if ( (LA18_150=='!'||(LA18_150>='$' && LA18_150<='%')||(LA18_150>='(' && LA18_150<=')')||(LA18_150>='+' && LA18_150<='/')||(LA18_150>=':' && LA18_150<='@')||(LA18_150>='[' && LA18_150<='_')||LA18_150=='|'||LA18_150=='\u00A8') ) {s = 152;}

                        else if ( (LA18_150=='\u00C3'||LA18_150=='\u00E0'||(LA18_150>='\u00E8' && LA18_150<='\u00E9')||LA18_150=='\u00EC'||LA18_150=='\u00F2'||LA18_150=='\u00F9') ) {s = 153;}

                        else if ( ((LA18_150>='\u0000' && LA18_150<='\b')||(LA18_150>='\u000B' && LA18_150<='\f')||(LA18_150>='\u000E' && LA18_150<='\u001F')||LA18_150=='#'||LA18_150=='&'||LA18_150=='*'||LA18_150=='`'||LA18_150=='{'||(LA18_150>='}' && LA18_150<='\u00A7')||(LA18_150>='\u00A9' && LA18_150<='\u00C2')||(LA18_150>='\u00C4' && LA18_150<='\u00DF')||(LA18_150>='\u00E1' && LA18_150<='\u00E7')||(LA18_150>='\u00EA' && LA18_150<='\u00EB')||(LA18_150>='\u00ED' && LA18_150<='\u00F1')||(LA18_150>='\u00F3' && LA18_150<='\u00F8')||(LA18_150>='\u00FA' && LA18_150<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}