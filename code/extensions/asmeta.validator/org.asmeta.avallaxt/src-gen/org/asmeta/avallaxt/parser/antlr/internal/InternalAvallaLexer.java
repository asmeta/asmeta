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
    public static final int RULE_STRING=8;
    public static final int RULE_SL_COMMENT=10;
    public static final int T__19=19;
    public static final int RULE_IN=6;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_GOOD_CHARS_NO_COLON=4;
    public static final int RULE_WS=11;
    public static final int RULE_LOCAL_VARIABLE=5;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int RULE_GOOD_CHAR_NO_COLON=12;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_RULE_NAME=7;

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

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
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
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
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
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
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
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
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
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
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
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
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
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
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
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
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
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
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
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:22:7: ( 'pick' )
            // InternalAvalla.g:22:9: 'pick'
            {
            match("pick"); 


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
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
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
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
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
    // $ANTLR end "T__27"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:1000:13: ( '\"' (~ ( '\"' ) )* '\"' )
            // InternalAvalla.g:1000:15: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // InternalAvalla.g:1000:19: (~ ( '\"' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAvalla.g:1000:19: ~ ( '\"' )
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
            // InternalAvalla.g:1002:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalAvalla.g:1002:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalAvalla.g:1002:24: ( options {greedy=false; } : . )*
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
            	    // InternalAvalla.g:1002:52: .
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
            // InternalAvalla.g:1004:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalAvalla.g:1004:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalAvalla.g:1004:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalAvalla.g:1004:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalAvalla.g:1004:40: ( ( '\\r' )? '\\n' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\n'||LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalAvalla.g:1004:41: ( '\\r' )? '\\n'
                    {
                    // InternalAvalla.g:1004:41: ( '\\r' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalAvalla.g:1004:41: '\\r'
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
            // InternalAvalla.g:1006:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalAvalla.g:1006:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalAvalla.g:1006:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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

    // $ANTLR start "RULE_IN"
    public final void mRULE_IN() throws RecognitionException {
        try {
            int _type = RULE_IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:1008:9: ( 'in' )
            // InternalAvalla.g:1008:11: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_IN"

    // $ANTLR start "RULE_RULE_NAME"
    public final void mRULE_RULE_NAME() throws RecognitionException {
        try {
            int _type = RULE_RULE_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:1010:16: ( 'r_' RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:1010:18: 'r_' RULE_GOOD_CHARS_NO_COLON
            {
            match("r_"); 

            mRULE_GOOD_CHARS_NO_COLON(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_RULE_NAME"

    // $ANTLR start "RULE_LOCAL_VARIABLE"
    public final void mRULE_LOCAL_VARIABLE() throws RecognitionException {
        try {
            int _type = RULE_LOCAL_VARIABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalAvalla.g:1012:21: ( '$' RULE_GOOD_CHARS_NO_COLON )
            // InternalAvalla.g:1012:23: '$' RULE_GOOD_CHARS_NO_COLON
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
            // InternalAvalla.g:1014:34: ( ( '!' .. '#' | '%' .. '9' | '<' .. '~' ) )
            // InternalAvalla.g:1014:36: ( '!' .. '#' | '%' .. '9' | '<' .. '~' )
            {
            if ( (input.LA(1)>='!' && input.LA(1)<='#')||(input.LA(1)>='%' && input.LA(1)<='9')||(input.LA(1)>='<' && input.LA(1)<='~') ) {
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
            // InternalAvalla.g:1016:26: ( ( RULE_GOOD_CHAR_NO_COLON )+ )
            // InternalAvalla.g:1016:28: ( RULE_GOOD_CHAR_NO_COLON )+
            {
            // InternalAvalla.g:1016:28: ( RULE_GOOD_CHAR_NO_COLON )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='!' && LA7_0<='#')||(LA7_0>='%' && LA7_0<='9')||(LA7_0>='<' && LA7_0<='~')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalAvalla.g:1016:28: RULE_GOOD_CHAR_NO_COLON
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
        // InternalAvalla.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_IN | RULE_RULE_NAME | RULE_LOCAL_VARIABLE | RULE_GOOD_CHARS_NO_COLON )
        int alt8=23;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // InternalAvalla.g:1:10: T__13
                {
                mT__13(); 

                }
                break;
            case 2 :
                // InternalAvalla.g:1:16: T__14
                {
                mT__14(); 

                }
                break;
            case 3 :
                // InternalAvalla.g:1:22: T__15
                {
                mT__15(); 

                }
                break;
            case 4 :
                // InternalAvalla.g:1:28: T__16
                {
                mT__16(); 

                }
                break;
            case 5 :
                // InternalAvalla.g:1:34: T__17
                {
                mT__17(); 

                }
                break;
            case 6 :
                // InternalAvalla.g:1:40: T__18
                {
                mT__18(); 

                }
                break;
            case 7 :
                // InternalAvalla.g:1:46: T__19
                {
                mT__19(); 

                }
                break;
            case 8 :
                // InternalAvalla.g:1:52: T__20
                {
                mT__20(); 

                }
                break;
            case 9 :
                // InternalAvalla.g:1:58: T__21
                {
                mT__21(); 

                }
                break;
            case 10 :
                // InternalAvalla.g:1:64: T__22
                {
                mT__22(); 

                }
                break;
            case 11 :
                // InternalAvalla.g:1:70: T__23
                {
                mT__23(); 

                }
                break;
            case 12 :
                // InternalAvalla.g:1:76: T__24
                {
                mT__24(); 

                }
                break;
            case 13 :
                // InternalAvalla.g:1:82: T__25
                {
                mT__25(); 

                }
                break;
            case 14 :
                // InternalAvalla.g:1:88: T__26
                {
                mT__26(); 

                }
                break;
            case 15 :
                // InternalAvalla.g:1:94: T__27
                {
                mT__27(); 

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
                // InternalAvalla.g:1:152: RULE_IN
                {
                mRULE_IN(); 

                }
                break;
            case 21 :
                // InternalAvalla.g:1:160: RULE_RULE_NAME
                {
                mRULE_RULE_NAME(); 

                }
                break;
            case 22 :
                // InternalAvalla.g:1:175: RULE_LOCAL_VARIABLE
                {
                mRULE_LOCAL_VARIABLE(); 

                }
                break;
            case 23 :
                // InternalAvalla.g:1:195: RULE_GOOD_CHARS_NO_COLON
                {
                mRULE_GOOD_CHARS_NO_COLON(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\3\20\1\27\1\uffff\7\20\1\uffff\1\20\2\uffff\4\20\1\51\2\uffff\7\20\1\40\1\uffff\1\20\1\64\2\20\1\67\3\20\1\uffff\3\20\1\76\4\20\1\uffff\1\64\1\uffff\1\102\1\20\1\uffff\1\104\1\105\3\20\1\112\1\uffff\1\113\1\20\1\62\1\uffff\1\20\2\uffff\1\20\1\117\1\120\1\20\2\uffff\1\122\2\20\2\uffff\1\20\1\uffff\3\20\1\131\2\20\1\uffff\1\134\1\135\2\uffff";
    static final String DFA8_eofS =
        "\136\uffff";
    static final String DFA8_minS =
        "\1\11\1\143\1\157\1\156\1\75\1\uffff\1\150\2\156\1\151\1\145\1\0\1\52\1\uffff\1\137\2\uffff\1\145\1\164\1\145\1\141\1\41\2\uffff\1\145\1\164\1\145\1\144\1\143\1\147\1\0\1\41\1\uffff\1\0\2\41\1\156\1\41\1\160\1\144\1\141\1\uffff\1\143\1\151\1\143\1\41\1\153\1\151\2\0\1\uffff\1\41\1\uffff\1\41\1\141\1\uffff\2\41\1\162\1\153\1\154\1\41\1\uffff\1\41\1\156\1\41\1\uffff\1\162\2\uffff\1\151\2\41\1\154\2\uffff\1\41\1\151\1\141\2\uffff\1\157\1\uffff\1\157\1\156\1\143\1\41\1\164\1\153\1\uffff\2\41\2\uffff";
    static final String DFA8_maxS =
        "\1\176\1\164\1\157\1\156\1\75\1\uffff\1\150\1\156\1\170\1\151\1\145\1\uffff\1\57\1\uffff\1\137\2\uffff\1\145\1\164\1\145\1\141\1\176\2\uffff\1\145\1\164\1\145\1\144\1\143\1\147\1\uffff\1\176\1\uffff\1\uffff\2\176\1\156\1\176\1\160\1\144\1\141\1\uffff\1\143\1\151\1\143\1\176\1\153\1\151\2\uffff\1\uffff\1\176\1\uffff\1\176\1\141\1\uffff\2\176\1\162\1\153\1\154\1\176\1\uffff\1\176\1\156\1\176\1\uffff\1\162\2\uffff\1\151\2\176\1\154\2\uffff\1\176\1\151\1\141\2\uffff\1\157\1\uffff\1\157\1\156\1\143\1\176\1\164\1\153\1\uffff\2\176\2\uffff";
    static final String DFA8_acceptS =
        "\5\uffff\1\5\7\uffff\1\23\1\uffff\1\26\1\27\5\uffff\1\10\1\4\10\uffff\1\20\10\uffff\1\24\10\uffff\1\21\1\uffff\1\22\2\uffff\1\7\6\uffff\1\16\3\uffff\1\25\1\uffff\1\11\1\2\4\uffff\1\13\1\14\3\uffff\1\6\1\12\1\uffff\1\15\6\uffff\1\1\2\uffff\1\3\1\17";
    static final String DFA8_specialS =
        "\13\uffff\1\0\22\uffff\1\2\2\uffff\1\3\16\uffff\1\1\1\4\54\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\15\2\uffff\1\15\22\uffff\1\15\1\20\1\13\1\20\1\17\12\20\1\14\12\20\1\4\1\5\46\20\1\12\1\6\1\20\1\10\3\20\1\3\2\20\1\2\3\20\1\11\1\20\1\16\1\1\1\20\1\7\11\20",
            "\1\21\1\uffff\1\22\16\uffff\1\23",
            "\1\24",
            "\1\25",
            "\1\26",
            "",
            "\1\30",
            "\1\31",
            "\1\33\11\uffff\1\32",
            "\1\34",
            "\1\35",
            "\41\40\1\36\1\37\1\36\1\40\25\36\2\40\103\36\uff81\40",
            "\1\41\4\uffff\1\42",
            "",
            "\1\43",
            "",
            "",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\3\20\1\uffff\25\20\2\uffff\72\20\1\50\10\20",
            "",
            "",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\41\40\1\36\1\37\1\36\1\40\25\36\2\40\103\36\uff81\40",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "",
            "\41\62\3\61\1\62\5\61\1\60\17\61\2\62\103\61\uff81\62",
            "\3\63\1\uffff\25\63\2\uffff\103\63",
            "\3\65\1\uffff\25\65\2\uffff\103\65",
            "\1\66",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\70",
            "\1\71",
            "\1\72",
            "",
            "\1\73",
            "\1\74",
            "\1\75",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\77",
            "\1\100",
            "\41\62\3\61\1\62\5\61\1\60\4\61\1\101\12\61\2\62\103\61\uff81\62",
            "\41\62\3\61\1\62\5\61\1\60\17\61\2\62\103\61\uff81\62",
            "",
            "\3\63\1\uffff\25\63\2\uffff\103\63",
            "",
            "\3\65\1\uffff\25\65\2\uffff\103\65",
            "\1\103",
            "",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\106",
            "\1\107",
            "\1\110",
            "\3\20\1\uffff\25\20\2\uffff\46\20\1\111\34\20",
            "",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\114",
            "\3\61\1\uffff\5\61\1\60\17\61\2\uffff\103\61",
            "",
            "\1\115",
            "",
            "",
            "\1\116",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\121",
            "",
            "",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\123",
            "\1\124",
            "",
            "",
            "\1\125",
            "",
            "\1\126",
            "\1\127",
            "\1\130",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\1\132",
            "\1\133",
            "",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
            "\3\20\1\uffff\25\20\2\uffff\103\20",
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
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_IN | RULE_RULE_NAME | RULE_LOCAL_VARIABLE | RULE_GOOD_CHARS_NO_COLON );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_11 = input.LA(1);

                        s = -1;
                        if ( (LA8_11=='!'||LA8_11=='#'||(LA8_11>='%' && LA8_11<='9')||(LA8_11>='<' && LA8_11<='~')) ) {s = 30;}

                        else if ( (LA8_11=='\"') ) {s = 31;}

                        else if ( ((LA8_11>='\u0000' && LA8_11<=' ')||LA8_11=='$'||(LA8_11>=':' && LA8_11<=';')||(LA8_11>='\u007F' && LA8_11<='\uFFFF')) ) {s = 32;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_48 = input.LA(1);

                        s = -1;
                        if ( (LA8_48=='/') ) {s = 65;}

                        else if ( (LA8_48=='*') ) {s = 48;}

                        else if ( ((LA8_48>='!' && LA8_48<='#')||(LA8_48>='%' && LA8_48<=')')||(LA8_48>='+' && LA8_48<='.')||(LA8_48>='0' && LA8_48<='9')||(LA8_48>='<' && LA8_48<='~')) ) {s = 49;}

                        else if ( ((LA8_48>='\u0000' && LA8_48<=' ')||LA8_48=='$'||(LA8_48>=':' && LA8_48<=';')||(LA8_48>='\u007F' && LA8_48<='\uFFFF')) ) {s = 50;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_30 = input.LA(1);

                        s = -1;
                        if ( (LA8_30=='\"') ) {s = 31;}

                        else if ( (LA8_30=='!'||LA8_30=='#'||(LA8_30>='%' && LA8_30<='9')||(LA8_30>='<' && LA8_30<='~')) ) {s = 30;}

                        else if ( ((LA8_30>='\u0000' && LA8_30<=' ')||LA8_30=='$'||(LA8_30>=':' && LA8_30<=';')||(LA8_30>='\u007F' && LA8_30<='\uFFFF')) ) {s = 32;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_33 = input.LA(1);

                        s = -1;
                        if ( (LA8_33=='*') ) {s = 48;}

                        else if ( ((LA8_33>='!' && LA8_33<='#')||(LA8_33>='%' && LA8_33<=')')||(LA8_33>='+' && LA8_33<='9')||(LA8_33>='<' && LA8_33<='~')) ) {s = 49;}

                        else if ( ((LA8_33>='\u0000' && LA8_33<=' ')||LA8_33=='$'||(LA8_33>=':' && LA8_33<=';')||(LA8_33>='\u007F' && LA8_33<='\uFFFF')) ) {s = 50;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_49 = input.LA(1);

                        s = -1;
                        if ( (LA8_49=='*') ) {s = 48;}

                        else if ( ((LA8_49>='!' && LA8_49<='#')||(LA8_49>='%' && LA8_49<=')')||(LA8_49>='+' && LA8_49<='9')||(LA8_49>='<' && LA8_49<='~')) ) {s = 49;}

                        else if ( ((LA8_49>='\u0000' && LA8_49<=' ')||LA8_49=='$'||(LA8_49>=':' && LA8_49<=';')||(LA8_49>='\u007F' && LA8_49<='\uFFFF')) ) {s = 50;}

                        else s = 16;

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