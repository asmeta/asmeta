package org.asmeta.avallaxt.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAvallaLexer extends Lexer {
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

    public InternalAvallaLexer() {;} 
    public InternalAvallaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalAvallaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalAvalla.g"; }

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:11:7: ( 'scenario' )
            // InternalAvalla.g:11:9: 'scenario'
            {
            match("scenario"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:12:7: ( 'load' )
            // InternalAvalla.g:12:9: 'load'
            {
            match("load"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:13:7: ( 'invariant' )
            // InternalAvalla.g:13:9: 'invariant'
            {
            match("invariant"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:14:7: ( ':' )
            // InternalAvalla.g:14:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:15:7: ( ';' )
            // InternalAvalla.g:15:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:16:7: ( 'check' )
            // InternalAvalla.g:16:9: 'check'
            {
            match("check"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:17:7: ( 'set' )
            // InternalAvalla.g:17:9: 'set'
            {
            match("set"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:18:7: ( ':=' )
            // InternalAvalla.g:18:9: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:19:7: ( 'step' )
            // InternalAvalla.g:19:9: 'step'
            {
            match("step"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:20:7: ( 'until' )
            // InternalAvalla.g:20:9: 'until'
            {
            match("until"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:21:7: ( 'exec' )
            // InternalAvalla.g:21:9: 'exec'
            {
            match("exec"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:22:7: ( 'begin' )
            // InternalAvalla.g:22:9: 'begin'
            {
            match("begin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:23:7: ( 'end' )
            // InternalAvalla.g:23:9: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:24:7: ( 'execblock' )
            // InternalAvalla.g:24:9: 'execblock'
            {
            match("execblock"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:831:13: ( '\"' (~ ( '\"' ) )* '\"' )
            // InternalAvalla.g:831:15: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // InternalAvalla.g:831:19: (~ ( '\"' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAvalla.g:831:19: ~ ( '\"' )
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
            	    break loop1;
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

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:833:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalAvalla.g:833:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalAvalla.g:833:24: ( options {greedy=false; } : . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='/') ) {
                        alt2=2;
                    }
                    else if ( ((LA2_1>='\u0000' && LA2_1<='.')||(LA2_1>='0' && LA2_1<='\uFFFF')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<=')')||(LA2_0>='+' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalAvalla.g:833:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
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
            // InternalAvalla.g:835:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalAvalla.g:835:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalAvalla.g:835:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalAvalla.g:835:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop3;
                }
            } while (true);

            // InternalAvalla.g:835:40: ( ( '\\r' )? '\\n' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\n'||LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalAvalla.g:835:41: ( '\\r' )? '\\n'
                    {
                    // InternalAvalla.g:835:41: ( '\\r' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalAvalla.g:835:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

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
            // InternalAvalla.g:837:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalAvalla.g:837:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalAvalla.g:837:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\t' && LA6_0<='\n')||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalAvalla.g:
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
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_GOOD_CHAR_NO_COLON"
    public final void mRULE_GOOD_CHAR_NO_COLON() throws RecognitionException {
        try {
            // InternalAvalla.g:839:34: ( ( '!' .. '9' | '<' .. '~' ) )
            // InternalAvalla.g:839:36: ( '!' .. '9' | '<' .. '~' )
            {
            if ( (input.LA(1)>='!' && input.LA(1)<='9')||(input.LA(1)>='<' && input.LA(1)<='~') ) {
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
    // $ANTLR end "RULE_GOOD_CHAR_NO_COLON"

    // $ANTLR start "RULE_GOOD_CHARS_NO_COLON"
    public final void mRULE_GOOD_CHARS_NO_COLON() throws RecognitionException {
        try {
            int _type = RULE_GOOD_CHARS_NO_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:841:26: ( ( RULE_GOOD_CHAR_NO_COLON )+ )
            // InternalAvalla.g:841:28: ( RULE_GOOD_CHAR_NO_COLON )+
            {
            // InternalAvalla.g:841:28: ( RULE_GOOD_CHAR_NO_COLON )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='!' && LA7_0<='9')||(LA7_0>='<' && LA7_0<='~')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalAvalla.g:841:28: RULE_GOOD_CHAR_NO_COLON
            	    {
            	    mRULE_GOOD_CHAR_NO_COLON(); 

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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_GOOD_CHARS_NO_COLON"

    public void mTokens() throws RecognitionException {
        // InternalAvalla.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_GOOD_CHARS_NO_COLON )
        int alt8=19;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // InternalAvalla.g:1:10: T__10
                {
                mT__10(); 

                }
                break;
            case 2 :
                // InternalAvalla.g:1:16: T__11
                {
                mT__11(); 

                }
                break;
            case 3 :
                // InternalAvalla.g:1:22: T__12
                {
                mT__12(); 

                }
                break;
            case 4 :
                // InternalAvalla.g:1:28: T__13
                {
                mT__13(); 

                }
                break;
            case 5 :
                // InternalAvalla.g:1:34: T__14
                {
                mT__14(); 

                }
                break;
            case 6 :
                // InternalAvalla.g:1:40: T__15
                {
                mT__15(); 

                }
                break;
            case 7 :
                // InternalAvalla.g:1:46: T__16
                {
                mT__16(); 

                }
                break;
            case 8 :
                // InternalAvalla.g:1:52: T__17
                {
                mT__17(); 

                }
                break;
            case 9 :
                // InternalAvalla.g:1:58: T__18
                {
                mT__18(); 

                }
                break;
            case 10 :
                // InternalAvalla.g:1:64: T__19
                {
                mT__19(); 

                }
                break;
            case 11 :
                // InternalAvalla.g:1:70: T__20
                {
                mT__20(); 

                }
                break;
            case 12 :
                // InternalAvalla.g:1:76: T__21
                {
                mT__21(); 

                }
                break;
            case 13 :
                // InternalAvalla.g:1:82: T__22
                {
                mT__22(); 

                }
                break;
            case 14 :
                // InternalAvalla.g:1:88: T__23
                {
                mT__23(); 

                }
                break;
            case 15 :
                // InternalAvalla.g:1:94: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 16 :
                // InternalAvalla.g:1:106: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 17 :
                // InternalAvalla.g:1:122: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 18 :
                // InternalAvalla.g:1:138: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 19 :
                // InternalAvalla.g:1:146: RULE_GOOD_CHARS_NO_COLON
                {
                mRULE_GOOD_CHARS_NO_COLON(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\3\15\1\24\1\uffff\6\15\2\uffff\5\15\2\uffff\6\15\1\34\1\uffff\1\15\1\55\1\15\1\57\6\15\1\66\3\15\1\uffff\1\55\1\uffff\1\15\1\uffff\1\72\1\73\3\15\1\100\1\uffff\1\15\1\53\1\15\2\uffff\1\15\1\104\1\105\1\15\1\uffff\1\107\2\15\2\uffff\1\15\1\uffff\3\15\1\116\2\15\1\uffff\1\121\1\122\2\uffff";
    static final String DFA8_eofS =
        "\123\uffff";
    static final String DFA8_minS =
        "\1\11\1\143\1\157\1\156\1\75\1\uffff\1\150\2\156\1\145\1\0\1\52\2\uffff\1\145\1\164\1\145\1\141\1\166\2\uffff\1\145\1\164\1\145\1\144\1\147\1\0\1\41\1\uffff\1\0\1\41\1\156\1\41\1\160\1\144\1\141\1\143\1\151\1\143\1\41\1\151\2\0\1\uffff\1\41\1\uffff\1\141\1\uffff\2\41\1\162\1\153\1\154\1\41\1\uffff\1\156\1\41\1\162\2\uffff\1\151\2\41\1\154\1\uffff\1\41\1\151\1\141\2\uffff\1\157\1\uffff\1\157\1\156\1\143\1\41\1\164\1\153\1\uffff\2\41\2\uffff";
    static final String DFA8_maxS =
        "\1\176\1\164\1\157\1\156\1\75\1\uffff\1\150\1\156\1\170\1\145\1\uffff\1\57\2\uffff\1\145\1\164\1\145\1\141\1\166\2\uffff\1\145\1\164\1\145\1\144\1\147\1\uffff\1\176\1\uffff\1\uffff\1\176\1\156\1\176\1\160\1\144\1\141\1\143\1\151\1\143\1\176\1\151\2\uffff\1\uffff\1\176\1\uffff\1\141\1\uffff\2\176\1\162\1\153\1\154\1\176\1\uffff\1\156\1\176\1\162\2\uffff\1\151\2\176\1\154\1\uffff\1\176\1\151\1\141\2\uffff\1\157\1\uffff\1\157\1\156\1\143\1\176\1\164\1\153\1\uffff\2\176\2\uffff";
    static final String DFA8_acceptS =
        "\5\uffff\1\5\6\uffff\1\22\1\23\5\uffff\1\10\1\4\7\uffff\1\17\16\uffff\1\20\1\uffff\1\21\1\uffff\1\7\6\uffff\1\15\3\uffff\1\11\1\2\4\uffff\1\13\3\uffff\1\6\1\12\1\uffff\1\14\6\uffff\1\1\2\uffff\1\3\1\16";
    static final String DFA8_specialS =
        "\12\uffff\1\2\17\uffff\1\0\2\uffff\1\1\13\uffff\1\4\1\3\50\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\14\2\uffff\1\14\22\uffff\1\14\1\15\1\12\14\15\1\13\12\15\1\4\1\5\46\15\1\11\1\6\1\15\1\10\3\15\1\3\2\15\1\2\6\15\1\1\1\15\1\7\11\15",
            "\1\16\1\uffff\1\17\16\uffff\1\20",
            "\1\21",
            "\1\22",
            "\1\23",
            "",
            "\1\25",
            "\1\26",
            "\1\30\11\uffff\1\27",
            "\1\31",
            "\41\34\1\32\1\33\27\32\2\34\103\32\uff81\34",
            "\1\35\4\uffff\1\36",
            "",
            "",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "",
            "",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\50",
            "\41\34\1\32\1\33\27\32\2\34\103\32\uff81\34",
            "\31\15\2\uffff\103\15",
            "",
            "\41\53\11\52\1\51\17\52\2\53\103\52\uff81\53",
            "\31\54\2\uffff\103\54",
            "\1\56",
            "\31\15\2\uffff\103\15",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\31\15\2\uffff\103\15",
            "\1\67",
            "\41\53\11\52\1\51\4\52\1\70\12\52\2\53\103\52\uff81\53",
            "\41\53\11\52\1\51\17\52\2\53\103\52\uff81\53",
            "",
            "\31\54\2\uffff\103\54",
            "",
            "\1\71",
            "",
            "\31\15\2\uffff\103\15",
            "\31\15\2\uffff\103\15",
            "\1\74",
            "\1\75",
            "\1\76",
            "\31\15\2\uffff\46\15\1\77\34\15",
            "",
            "\1\101",
            "\11\52\1\51\17\52\2\uffff\103\52",
            "\1\102",
            "",
            "",
            "\1\103",
            "\31\15\2\uffff\103\15",
            "\31\15\2\uffff\103\15",
            "\1\106",
            "",
            "\31\15\2\uffff\103\15",
            "\1\110",
            "\1\111",
            "",
            "",
            "\1\112",
            "",
            "\1\113",
            "\1\114",
            "\1\115",
            "\31\15\2\uffff\103\15",
            "\1\117",
            "\1\120",
            "",
            "\31\15\2\uffff\103\15",
            "\31\15\2\uffff\103\15",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_GOOD_CHARS_NO_COLON );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_26 = input.LA(1);

                        s = -1;
                        if ( (LA8_26=='\"') ) {s = 27;}

                        else if ( (LA8_26=='!'||(LA8_26>='#' && LA8_26<='9')||(LA8_26>='<' && LA8_26<='~')) ) {s = 26;}

                        else if ( ((LA8_26>='\u0000' && LA8_26<=' ')||(LA8_26>=':' && LA8_26<=';')||(LA8_26>='\u007F' && LA8_26<='\uFFFF')) ) {s = 28;}

                        else s = 13;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_29 = input.LA(1);

                        s = -1;
                        if ( (LA8_29=='*') ) {s = 41;}

                        else if ( ((LA8_29>='!' && LA8_29<=')')||(LA8_29>='+' && LA8_29<='9')||(LA8_29>='<' && LA8_29<='~')) ) {s = 42;}

                        else if ( ((LA8_29>='\u0000' && LA8_29<=' ')||(LA8_29>=':' && LA8_29<=';')||(LA8_29>='\u007F' && LA8_29<='\uFFFF')) ) {s = 43;}

                        else s = 13;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_10 = input.LA(1);

                        s = -1;
                        if ( (LA8_10=='!'||(LA8_10>='#' && LA8_10<='9')||(LA8_10>='<' && LA8_10<='~')) ) {s = 26;}

                        else if ( (LA8_10=='\"') ) {s = 27;}

                        else if ( ((LA8_10>='\u0000' && LA8_10<=' ')||(LA8_10>=':' && LA8_10<=';')||(LA8_10>='\u007F' && LA8_10<='\uFFFF')) ) {s = 28;}

                        else s = 13;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_42 = input.LA(1);

                        s = -1;
                        if ( (LA8_42=='*') ) {s = 41;}

                        else if ( ((LA8_42>='!' && LA8_42<=')')||(LA8_42>='+' && LA8_42<='9')||(LA8_42>='<' && LA8_42<='~')) ) {s = 42;}

                        else if ( ((LA8_42>='\u0000' && LA8_42<=' ')||(LA8_42>=':' && LA8_42<=';')||(LA8_42>='\u007F' && LA8_42<='\uFFFF')) ) {s = 43;}

                        else s = 13;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_41 = input.LA(1);

                        s = -1;
                        if ( (LA8_41=='/') ) {s = 56;}

                        else if ( (LA8_41=='*') ) {s = 41;}

                        else if ( ((LA8_41>='!' && LA8_41<=')')||(LA8_41>='+' && LA8_41<='.')||(LA8_41>='0' && LA8_41<='9')||(LA8_41>='<' && LA8_41<='~')) ) {s = 42;}

                        else if ( ((LA8_41>='\u0000' && LA8_41<=' ')||(LA8_41>=':' && LA8_41<=';')||(LA8_41>='\u007F' && LA8_41<='\uFFFF')) ) {s = 43;}

                        else s = 13;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}