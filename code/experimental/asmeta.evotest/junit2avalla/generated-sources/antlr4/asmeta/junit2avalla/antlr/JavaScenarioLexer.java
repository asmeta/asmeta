// Generated from asmeta\junit2avalla\antlr\JavaScenario.g4 by ANTLR 4.7.1
package asmeta.junit2avalla.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaScenarioLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, LINE_COMMENT=2, ClassDeclaration=3, TestAnnotation=4, TestDeclaration=5, 
		SetFunc=6, StepFunc=7, Getter=8, TryCatch=9, Identifier=10, STRING=11, 
		ASMID=12, ASSERT_EQUALS=13, AND=14, OR=15, NOT=16, EQ=17, COMMA=18, COLONS=19, 
		SEMI=20, LPAREN=21, RPAREN=22, LCURLY=23, RCURLY=24, DOT=25, START=26, 
		AT=27, DOUBLE_QUOTES=28, NEW=29, SET=30, STEP=31, GET=32, TRY=33, CATCH=34, 
		INT=35, ID=36, WS=37;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"TEXT", "ESC_SEQ", "COMMENT", "LINE_COMMENT", "ClassDeclaration", "TestAnnotation", 
		"TestDeclaration", "SetFunc", "StepFunc", "Getter", "TryCatch", "Identifier", 
		"STRING", "ASMID", "ASSERT_EQUALS", "AND", "OR", "NOT", "EQ", "COMMA", 
		"COLONS", "SEMI", "LPAREN", "RPAREN", "LCURLY", "RCURLY", "DOT", "START", 
		"AT", "DOUBLE_QUOTES", "NEW", "SET", "STEP", "GET", "TRY", "CATCH", "INT", 
		"ID", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "'assertEquals'", "'&&'", "'||'", "'!'", "'='", "','", "':'", "';'", 
		"'('", "')'", "'{'", "'}'", "'.'", "'*'", "'@'", "'\"'", "'new'", "'set_'", 
		"'step'", "'get_'", "'try'", "'catch'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "COMMENT", "LINE_COMMENT", "ClassDeclaration", "TestAnnotation", 
		"TestDeclaration", "SetFunc", "StepFunc", "Getter", "TryCatch", "Identifier", 
		"STRING", "ASMID", "ASSERT_EQUALS", "AND", "OR", "NOT", "EQ", "COMMA", 
		"COLONS", "SEMI", "LPAREN", "RPAREN", "LCURLY", "RCURLY", "DOT", "START", 
		"AT", "DOUBLE_QUOTES", "NEW", "SET", "STEP", "GET", "TRY", "CATCH", "INT", 
		"ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public JavaScenarioLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JavaScenario.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\'\u0149\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\7\2T\n\2\f\2"+
		"\16\2W\13\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4`\n\4\f\4\16\4c\13\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5n\n\5\f\5\16\5q\13\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0082\n\6\3\6\7\6\u0085"+
		"\n\6\f\6\16\6\u0088\13\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u0094"+
		"\n\7\f\7\16\7\u0097\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00a3"+
		"\n\b\f\b\16\b\u00a6\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00c0\n"+
		"\f\f\f\16\f\u00c3\13\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00cd\n\f\3"+
		"\f\3\f\5\f\u00d1\n\f\3\f\3\f\3\r\3\r\3\r\3\r\6\r\u00d9\n\r\r\r\16\r\u00da"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\7\17\u00e3\n\17\f\17\16\17\u00e6\13\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3!\3"+
		"\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3&\6&"+
		"\u0138\n&\r&\16&\u0139\3\'\3\'\7\'\u013e\n\'\f\'\16\'\u0141\13\'\3(\6"+
		"(\u0144\n(\r(\16(\u0145\3(\3(\7a\u0086\u0095\u00a4\u00c1\2)\3\2\5\2\7"+
		"\3\t\4\13\5\r\6\17\7\21\b\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21"+
		"%\22\'\23)\24+\25-\26/\27\61\30\63\31\65\32\67\339\34;\35=\36?\37A C!"+
		"E\"G#I$K%M&O\'\3\2\t\4\2$$^^\n\2$$))^^ddhhppttvv\4\2\f\f\17\17\5\2C\\"+
		"aac|\6\2\62;C\\aac|\3\2\62;\5\2\13\f\16\17\"\"\2\u0156\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\3U\3\2\2\2\5X\3\2\2\2\7[\3\2\2\2\ti\3\2\2\2\13\u0081\3\2\2\2\r"+
		"\u008b\3\2\2\2\17\u009a\3\2\2\2\21\u00a9\3\2\2\2\23\u00ae\3\2\2\2\25\u00b2"+
		"\3\2\2\2\27\u00b9\3\2\2\2\31\u00d4\3\2\2\2\33\u00dc\3\2\2\2\35\u00e0\3"+
		"\2\2\2\37\u00ec\3\2\2\2!\u00f9\3\2\2\2#\u00fc\3\2\2\2%\u00ff\3\2\2\2\'"+
		"\u0101\3\2\2\2)\u0103\3\2\2\2+\u0105\3\2\2\2-\u0107\3\2\2\2/\u0109\3\2"+
		"\2\2\61\u010b\3\2\2\2\63\u010d\3\2\2\2\65\u010f\3\2\2\2\67\u0111\3\2\2"+
		"\29\u0113\3\2\2\2;\u0115\3\2\2\2=\u0117\3\2\2\2?\u0119\3\2\2\2A\u011d"+
		"\3\2\2\2C\u0122\3\2\2\2E\u0127\3\2\2\2G\u012c\3\2\2\2I\u0130\3\2\2\2K"+
		"\u0137\3\2\2\2M\u013b\3\2\2\2O\u0143\3\2\2\2QT\5\5\3\2RT\n\2\2\2SQ\3\2"+
		"\2\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2V\4\3\2\2\2WU\3\2\2\2XY\7"+
		"^\2\2YZ\t\3\2\2Z\6\3\2\2\2[\\\7\61\2\2\\]\7,\2\2]a\3\2\2\2^`\13\2\2\2"+
		"_^\3\2\2\2`c\3\2\2\2ab\3\2\2\2a_\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\7,\2\2"+
		"ef\7\61\2\2fg\3\2\2\2gh\b\4\2\2h\b\3\2\2\2ij\7\61\2\2jk\7\61\2\2ko\3\2"+
		"\2\2ln\n\4\2\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2"+
		"\2\2rs\b\5\2\2s\n\3\2\2\2tu\7r\2\2uv\7c\2\2vw\7e\2\2wx\7m\2\2xy\7c\2\2"+
		"yz\7i\2\2z\u0082\7g\2\2{|\7k\2\2|}\7o\2\2}~\7r\2\2~\177\7q\2\2\177\u0080"+
		"\7t\2\2\u0080\u0082\7v\2\2\u0081t\3\2\2\2\u0081{\3\2\2\2\u0082\u0086\3"+
		"\2\2\2\u0083\u0085\13\2\2\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0089\3\2\2\2\u0088\u0086\3\2"+
		"\2\2\u0089\u008a\5\63\32\2\u008a\f\3\2\2\2\u008b\u008c\7B\2\2\u008c\u008d"+
		"\7V\2\2\u008d\u008e\7g\2\2\u008e\u008f\7u\2\2\u008f\u0090\7v\2\2\u0090"+
		"\u0091\3\2\2\2\u0091\u0095\5/\30\2\u0092\u0094\13\2\2\2\u0093\u0092\3"+
		"\2\2\2\u0094\u0097\3\2\2\2\u0095\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096"+
		"\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u0099\5\61\31\2\u0099\16\3\2"+
		"\2\2\u009a\u009b\7r\2\2\u009b\u009c\7w\2\2\u009c\u009d\7d\2\2\u009d\u009e"+
		"\7n\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7e\2\2\u00a0\u00a4\3\2\2\2\u00a1"+
		"\u00a3\13\2\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a5\3"+
		"\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7"+
		"\u00a8\5\63\32\2\u00a8\20\3\2\2\2\u00a9\u00aa\5M\'\2\u00aa\u00ab\5\67"+
		"\34\2\u00ab\u00ac\5A!\2\u00ac\u00ad\5M\'\2\u00ad\22\3\2\2\2\u00ae\u00af"+
		"\5M\'\2\u00af\u00b0\5\67\34\2\u00b0\u00b1\5C\"\2\u00b1\24\3\2\2\2\u00b2"+
		"\u00b3\5M\'\2\u00b3\u00b4\5\67\34\2\u00b4\u00b5\5E#\2\u00b5\u00b6\5M\'"+
		"\2\u00b6\u00b7\5/\30\2\u00b7\u00b8\5\61\31\2\u00b8\26\3\2\2\2\u00b9\u00ba"+
		"\7v\2\2\u00ba\u00bb\7t\2\2\u00bb\u00bc\7{\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00c1\5\65\33\2\u00be\u00c0\13\2\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c3"+
		"\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c4\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c4\u00c5\5\63\32\2\u00c5\u00c6\7e\2\2\u00c6\u00c7\7"+
		"c\2\2\u00c7\u00c8\7v\2\2\u00c8\u00c9\7e\2\2\u00c9\u00ca\7j\2\2\u00ca\u00cc"+
		"\3\2\2\2\u00cb\u00cd\13\2\2\2\u00cc\u00cb\3\2\2\2\u00cc\u00cd\3\2\2\2"+
		"\u00cd\u00ce\3\2\2\2\u00ce\u00d0\5\65\33\2\u00cf\u00d1\13\2\2\2\u00d0"+
		"\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\5\63"+
		"\32\2\u00d3\30\3\2\2\2\u00d4\u00d8\5M\'\2\u00d5\u00d6\5\67\34\2\u00d6"+
		"\u00d7\5M\'\2\u00d7\u00d9\3\2\2\2\u00d8\u00d5\3\2\2\2\u00d9\u00da\3\2"+
		"\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\32\3\2\2\2\u00dc\u00dd"+
		"\5=\37\2\u00dd\u00de\5\3\2\2\u00de\u00df\5=\37\2\u00df\34\3\2\2\2\u00e0"+
		"\u00e4\t\5\2\2\u00e1\u00e3\t\6\2\2\u00e2\u00e1\3\2\2\2\u00e3\u00e6\3\2"+
		"\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e7\u00e8\7a\2\2\u00e8\u00e9\7C\2\2\u00e9\u00ea\7V\2"+
		"\2\u00ea\u00eb\7I\2\2\u00eb\36\3\2\2\2\u00ec\u00ed\7c\2\2\u00ed\u00ee"+
		"\7u\2\2\u00ee\u00ef\7u\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7t\2\2\u00f1"+
		"\u00f2\7v\2\2\u00f2\u00f3\7G\2\2\u00f3\u00f4\7s\2\2\u00f4\u00f5\7w\2\2"+
		"\u00f5\u00f6\7c\2\2\u00f6\u00f7\7n\2\2\u00f7\u00f8\7u\2\2\u00f8 \3\2\2"+
		"\2\u00f9\u00fa\7(\2\2\u00fa\u00fb\7(\2\2\u00fb\"\3\2\2\2\u00fc\u00fd\7"+
		"~\2\2\u00fd\u00fe\7~\2\2\u00fe$\3\2\2\2\u00ff\u0100\7#\2\2\u0100&\3\2"+
		"\2\2\u0101\u0102\7?\2\2\u0102(\3\2\2\2\u0103\u0104\7.\2\2\u0104*\3\2\2"+
		"\2\u0105\u0106\7<\2\2\u0106,\3\2\2\2\u0107\u0108\7=\2\2\u0108.\3\2\2\2"+
		"\u0109\u010a\7*\2\2\u010a\60\3\2\2\2\u010b\u010c\7+\2\2\u010c\62\3\2\2"+
		"\2\u010d\u010e\7}\2\2\u010e\64\3\2\2\2\u010f\u0110\7\177\2\2\u0110\66"+
		"\3\2\2\2\u0111\u0112\7\60\2\2\u01128\3\2\2\2\u0113\u0114\7,\2\2\u0114"+
		":\3\2\2\2\u0115\u0116\7B\2\2\u0116<\3\2\2\2\u0117\u0118\7$\2\2\u0118>"+
		"\3\2\2\2\u0119\u011a\7p\2\2\u011a\u011b\7g\2\2\u011b\u011c\7y\2\2\u011c"+
		"@\3\2\2\2\u011d\u011e\7u\2\2\u011e\u011f\7g\2\2\u011f\u0120\7v\2\2\u0120"+
		"\u0121\7a\2\2\u0121B\3\2\2\2\u0122\u0123\7u\2\2\u0123\u0124\7v\2\2\u0124"+
		"\u0125\7g\2\2\u0125\u0126\7r\2\2\u0126D\3\2\2\2\u0127\u0128\7i\2\2\u0128"+
		"\u0129\7g\2\2\u0129\u012a\7v\2\2\u012a\u012b\7a\2\2\u012bF\3\2\2\2\u012c"+
		"\u012d\7v\2\2\u012d\u012e\7t\2\2\u012e\u012f\7{\2\2\u012fH\3\2\2\2\u0130"+
		"\u0131\7e\2\2\u0131\u0132\7c\2\2\u0132\u0133\7v\2\2\u0133\u0134\7e\2\2"+
		"\u0134\u0135\7j\2\2\u0135J\3\2\2\2\u0136\u0138\t\7\2\2\u0137\u0136\3\2"+
		"\2\2\u0138\u0139\3\2\2\2\u0139\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a"+
		"L\3\2\2\2\u013b\u013f\t\5\2\2\u013c\u013e\t\6\2\2\u013d\u013c\3\2\2\2"+
		"\u013e\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140N\3"+
		"\2\2\2\u0141\u013f\3\2\2\2\u0142\u0144\t\b\2\2\u0143\u0142\3\2\2\2\u0144"+
		"\u0145\3\2\2\2\u0145\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0147\3\2"+
		"\2\2\u0147\u0148\b(\2\2\u0148P\3\2\2\2\23\2SUao\u0081\u0086\u0095\u00a4"+
		"\u00c1\u00cc\u00d0\u00da\u00e4\u0139\u013f\u0145\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}