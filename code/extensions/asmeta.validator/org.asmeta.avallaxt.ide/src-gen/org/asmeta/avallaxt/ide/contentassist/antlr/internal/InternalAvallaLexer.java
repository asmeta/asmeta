package org.asmeta.avallaxt.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAvallaLexer extends Lexer {
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

    public InternalAvallaLexer() {;} 
    public InternalAvallaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalAvallaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalAvalla.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:11:7: ( 'step' )
            // InternalAvalla.g:11:9: 'step'
            {
            match("step"); 


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
            // InternalAvalla.g:12:7: ( ':' )
            // InternalAvalla.g:12:9: ':'
            {
            match(':'); 

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
            // InternalAvalla.g:13:7: ( ':=' )
            // InternalAvalla.g:13:9: ':='
            {
            match(":="); 


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
            // InternalAvalla.g:14:7: ( 'scenario' )
            // InternalAvalla.g:14:9: 'scenario'
            {
            match("scenario"); 


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
            // InternalAvalla.g:15:7: ( 'load' )
            // InternalAvalla.g:15:9: 'load'
            {
            match("load"); 


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
            // InternalAvalla.g:16:7: ( 'invariant' )
            // InternalAvalla.g:16:9: 'invariant'
            {
            match("invariant"); 


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
            // InternalAvalla.g:17:7: ( ';' )
            // InternalAvalla.g:17:9: ';'
            {
            match(';'); 

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
            // InternalAvalla.g:18:7: ( 'check' )
            // InternalAvalla.g:18:9: 'check'
            {
            match("check"); 


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
            // InternalAvalla.g:19:7: ( 'set' )
            // InternalAvalla.g:19:9: 'set'
            {
            match("set"); 


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
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
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
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:22:7: ( 'setchoose' )
            // InternalAvalla.g:22:9: 'setchoose'
            {
            match("setchoose"); 


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
            // InternalAvalla.g:23:7: ( 'begin' )
            // InternalAvalla.g:23:9: 'begin'
            {
            match("begin"); 


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
            // InternalAvalla.g:24:7: ( 'end' )
            // InternalAvalla.g:24:9: 'end'
            {
            match("end"); 


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
            // InternalAvalla.g:25:7: ( 'execblock' )
            // InternalAvalla.g:25:9: 'execblock'
            {
            match("execblock"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:2050:13: ( '\"' (~ ( '\"' ) )* '\"' )
            // InternalAvalla.g:2050:15: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // InternalAvalla.g:2050:19: (~ ( '\"' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAvalla.g:2050:19: ~ ( '\"' )
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
            // InternalAvalla.g:2052:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalAvalla.g:2052:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalAvalla.g:2052:24: ( options {greedy=false; } : . )*
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
            	    // InternalAvalla.g:2052:52: .
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
            // InternalAvalla.g:2054:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalAvalla.g:2054:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalAvalla.g:2054:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalAvalla.g:2054:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalAvalla.g:2054:40: ( ( '\\r' )? '\\n' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\n'||LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalAvalla.g:2054:41: ( '\\r' )? '\\n'
                    {
                    // InternalAvalla.g:2054:41: ( '\\r' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalAvalla.g:2054:41: '\\r'
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
            // InternalAvalla.g:2056:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalAvalla.g:2056:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalAvalla.g:2056:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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

    // $ANTLR start "RULE_LOCAL_VARIABLE"
    public final void mRULE_LOCAL_VARIABLE() throws RecognitionException {
        try {
            int _type = RULE_LOCAL_VARIABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:2058:21: ( '$' RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:2058:23: '$' RULE_GOOD_CHARS_NO_COLON
            {
            match('$'); 
            mRULE_GOOD_CHARS_NO_COLON(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_LOCAL_VARIABLE"

    // $ANTLR start "RULE_GOOD_CHAR_NO_COLON"
    public final void mRULE_GOOD_CHAR_NO_COLON() throws RecognitionException {
        try {
            // InternalAvalla.g:2060:34: ( ( '!' .. '9' | '<' .. '~' ) )
            // InternalAvalla.g:2060:36: ( '!' .. '9' | '<' .. '~' )
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
            // InternalAvalla.g:2062:26: ( ( RULE_GOOD_CHAR_NO_COLON )+ )
            // InternalAvalla.g:2062:28: ( RULE_GOOD_CHAR_NO_COLON )+
            {
            // InternalAvalla.g:2062:28: ( RULE_GOOD_CHAR_NO_COLON )+
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
            	    // InternalAvalla.g:2062:28: RULE_GOOD_CHAR_NO_COLON
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
        // InternalAvalla.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_LOCAL_VARIABLE | RULE_GOOD_CHARS_NO_COLON )
        int alt8=21;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // InternalAvalla.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // InternalAvalla.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // InternalAvalla.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // InternalAvalla.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // InternalAvalla.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // InternalAvalla.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // InternalAvalla.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // InternalAvalla.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // InternalAvalla.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // InternalAvalla.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // InternalAvalla.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // InternalAvalla.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // InternalAvalla.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // InternalAvalla.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // InternalAvalla.g:1:94: T__25
                {
                mT__25(); 

                }
                break;
            case 16 :
                // InternalAvalla.g:1:100: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 17 :
                // InternalAvalla.g:1:112: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 18 :
                // InternalAvalla.g:1:128: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 19 :
                // InternalAvalla.g:1:144: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 20 :
                // InternalAvalla.g:1:152: RULE_LOCAL_VARIABLE
                {
                mRULE_LOCAL_VARIABLE(); 

                }
                break;
            case 21 :
                // InternalAvalla.g:1:172: RULE_GOOD_CHARS_NO_COLON
                {
                mRULE_GOOD_CHARS_NO_COLON(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\1\16\1\23\2\16\1\uffff\6\16\1\uffff\1\16\1\uffff\3\16\2\uffff\10\16\1\35\1\uffff\1\16\1\56\1\60\2\16\1\64\5\16\1\72\2\16\1\uffff\1\16\1\uffff\1\56\1\uffff\1\75\2\16\1\uffff\1\100\3\16\1\105\1\uffff\1\16\1\54\1\uffff\2\16\1\uffff\1\16\1\112\1\113\1\16\1\uffff\1\115\3\16\2\uffff\1\16\1\uffff\4\16\1\126\3\16\1\uffff\1\132\1\133\1\134\3\uffff";
    static final String DFA8_eofS =
        "\135\uffff";
    static final String DFA8_minS =
        "\1\11\1\143\1\75\1\157\1\156\1\uffff\1\150\2\156\1\145\1\0\1\52\1\uffff\1\41\1\uffff\2\145\1\164\2\uffff\1\141\1\166\1\145\1\164\1\145\1\144\1\147\1\0\1\41\1\uffff\1\0\2\41\1\160\1\156\1\41\1\144\1\141\1\143\1\151\1\143\1\41\1\151\1\0\1\uffff\1\0\1\uffff\1\41\1\uffff\1\41\1\141\1\150\1\uffff\1\41\1\162\1\153\1\154\1\41\1\uffff\1\156\1\41\1\uffff\1\162\1\157\1\uffff\1\151\2\41\1\154\1\uffff\1\41\1\151\1\157\1\141\2\uffff\1\157\1\uffff\1\157\1\163\1\156\1\143\1\41\1\145\1\164\1\153\1\uffff\3\41\3\uffff";
    static final String DFA8_maxS =
        "\1\176\1\164\1\75\1\157\1\156\1\uffff\1\150\1\156\1\170\1\145\1\uffff\1\57\1\uffff\1\176\1\uffff\2\145\1\164\2\uffff\1\141\1\166\1\145\1\164\1\145\1\144\1\147\1\uffff\1\176\1\uffff\1\uffff\2\176\1\160\1\156\1\176\1\144\1\141\1\143\1\151\1\143\1\176\1\151\1\uffff\1\uffff\1\uffff\1\uffff\1\176\1\uffff\1\176\1\141\1\150\1\uffff\1\176\1\162\1\153\1\154\1\176\1\uffff\1\156\1\176\1\uffff\1\162\1\157\1\uffff\1\151\2\176\1\154\1\uffff\1\176\1\151\1\157\1\141\2\uffff\1\157\1\uffff\1\157\1\163\1\156\1\143\1\176\1\145\1\164\1\153\1\uffff\3\176\3\uffff";
    static final String DFA8_acceptS =
        "\5\uffff\1\7\6\uffff\1\23\1\uffff\1\25\3\uffff\1\3\1\2\11\uffff\1\20\16\uffff\1\21\1\uffff\1\22\1\uffff\1\24\3\uffff\1\11\5\uffff\1\16\2\uffff\1\1\2\uffff\1\5\4\uffff\1\13\4\uffff\1\10\1\12\1\uffff\1\15\10\uffff\1\4\3\uffff\1\14\1\6\1\17";
    static final String DFA8_specialS =
        "\12\uffff\1\0\20\uffff\1\2\2\uffff\1\3\14\uffff\1\1\1\uffff\1\4\57\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\14\2\uffff\1\14\22\uffff\1\14\1\16\1\12\1\16\1\15\12\16\1\13\12\16\1\2\1\5\46\16\1\11\1\6\1\16\1\10\3\16\1\4\2\16\1\3\6\16\1\1\1\16\1\7\11\16",
            "\1\20\1\uffff\1\21\16\uffff\1\17",
            "\1\22",
            "\1\24",
            "\1\25",
            "",
            "\1\26",
            "\1\27",
            "\1\31\11\uffff\1\30",
            "\1\32",
            "\41\35\1\33\1\34\27\33\2\35\103\33\uff81\35",
            "\1\36\4\uffff\1\37",
            "",
            "\31\40\2\uffff\103\40",
            "",
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
            "\1\51",
            "\1\52",
            "\41\35\1\33\1\34\27\33\2\35\103\33\uff81\35",
            "\31\16\2\uffff\103\16",
            "",
            "\41\54\11\55\1\53\17\55\2\54\103\55\uff81\54",
            "\31\57\2\uffff\103\57",
            "\31\40\2\uffff\103\40",
            "\1\61",
            "\1\62",
            "\31\16\2\uffff\47\16\1\63\33\16",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\31\16\2\uffff\103\16",
            "\1\73",
            "\41\54\11\55\1\53\4\55\1\74\12\55\2\54\103\55\uff81\54",
            "",
            "\41\54\11\55\1\53\17\55\2\54\103\55\uff81\54",
            "",
            "\31\57\2\uffff\103\57",
            "",
            "\31\16\2\uffff\103\16",
            "\1\76",
            "\1\77",
            "",
            "\31\16\2\uffff\103\16",
            "\1\101",
            "\1\102",
            "\1\103",
            "\31\16\2\uffff\46\16\1\104\34\16",
            "",
            "\1\106",
            "\11\55\1\53\17\55\2\uffff\103\55",
            "",
            "\1\107",
            "\1\110",
            "",
            "\1\111",
            "\31\16\2\uffff\103\16",
            "\31\16\2\uffff\103\16",
            "\1\114",
            "",
            "\31\16\2\uffff\103\16",
            "\1\116",
            "\1\117",
            "\1\120",
            "",
            "",
            "\1\121",
            "",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\31\16\2\uffff\103\16",
            "\1\127",
            "\1\130",
            "\1\131",
            "",
            "\31\16\2\uffff\103\16",
            "\31\16\2\uffff\103\16",
            "\31\16\2\uffff\103\16",
            "",
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
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_LOCAL_VARIABLE | RULE_GOOD_CHARS_NO_COLON );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_10 = input.LA(1);

                        s = -1;
                        if ( (LA8_10=='!'||(LA8_10>='#' && LA8_10<='9')||(LA8_10>='<' && LA8_10<='~')) ) {s = 27;}

                        else if ( (LA8_10=='\"') ) {s = 28;}

                        else if ( ((LA8_10>='\u0000' && LA8_10<=' ')||(LA8_10>=':' && LA8_10<=';')||(LA8_10>='\u007F' && LA8_10<='\uFFFF')) ) {s = 29;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_43 = input.LA(1);

                        s = -1;
                        if ( (LA8_43=='/') ) {s = 60;}

                        else if ( (LA8_43=='*') ) {s = 43;}

                        else if ( ((LA8_43>='!' && LA8_43<=')')||(LA8_43>='+' && LA8_43<='.')||(LA8_43>='0' && LA8_43<='9')||(LA8_43>='<' && LA8_43<='~')) ) {s = 45;}

                        else if ( ((LA8_43>='\u0000' && LA8_43<=' ')||(LA8_43>=':' && LA8_43<=';')||(LA8_43>='\u007F' && LA8_43<='\uFFFF')) ) {s = 44;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_27 = input.LA(1);

                        s = -1;
                        if ( (LA8_27=='\"') ) {s = 28;}

                        else if ( (LA8_27=='!'||(LA8_27>='#' && LA8_27<='9')||(LA8_27>='<' && LA8_27<='~')) ) {s = 27;}

                        else if ( ((LA8_27>='\u0000' && LA8_27<=' ')||(LA8_27>=':' && LA8_27<=';')||(LA8_27>='\u007F' && LA8_27<='\uFFFF')) ) {s = 29;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_30 = input.LA(1);

                        s = -1;
                        if ( (LA8_30=='*') ) {s = 43;}

                        else if ( ((LA8_30>='\u0000' && LA8_30<=' ')||(LA8_30>=':' && LA8_30<=';')||(LA8_30>='\u007F' && LA8_30<='\uFFFF')) ) {s = 44;}

                        else if ( ((LA8_30>='!' && LA8_30<=')')||(LA8_30>='+' && LA8_30<='9')||(LA8_30>='<' && LA8_30<='~')) ) {s = 45;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_45 = input.LA(1);

                        s = -1;
                        if ( (LA8_45=='*') ) {s = 43;}

                        else if ( ((LA8_45>='!' && LA8_45<=')')||(LA8_45>='+' && LA8_45<='9')||(LA8_45>='<' && LA8_45<='~')) ) {s = 45;}

                        else if ( ((LA8_45>='\u0000' && LA8_45<=' ')||(LA8_45>=':' && LA8_45<=';')||(LA8_45>='\u007F' && LA8_45<='\uFFFF')) ) {s = 44;}

                        else s = 14;

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