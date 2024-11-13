// Generated from asmeta\junit2avalla\antlr\JavaScenario.g4 by ANTLR 4.7.1
package asmeta.junit2avalla.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaScenarioParser extends Parser {
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
	public static final int
		RULE_start = 0, RULE_test = 1, RULE_scenario = 2, RULE_asmDeclaration = 3, 
		RULE_variableDeclaration = 4, RULE_variableType = 5, RULE_variableName = 6, 
		RULE_variableValue = 7, RULE_assertEquals = 8, RULE_actual = 9, RULE_expected = 10, 
		RULE_setFunction = 11, RULE_stepFunction = 12, RULE_setVariableValue = 13, 
		RULE_trycatchblock = 14;
	public static final String[] ruleNames = {
		"start", "test", "scenario", "asmDeclaration", "variableDeclaration", 
		"variableType", "variableName", "variableValue", "assertEquals", "actual", 
		"expected", "setFunction", "stepFunction", "setVariableValue", "trycatchblock"
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

	@Override
	public String getGrammarFileName() { return "JavaScenario.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavaScenarioParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public TerminalNode ClassDeclaration() { return getToken(JavaScenarioParser.ClassDeclaration, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public TerminalNode RCURLY() { return getToken(JavaScenarioParser.RCURLY, 0); }
		public TerminalNode EOF() { return getToken(JavaScenarioParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitStart(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(ClassDeclaration);
			setState(31);
			test();
			setState(32);
			match(RCURLY);
			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TestContext extends ParserRuleContext {
		public List<TerminalNode> TestAnnotation() { return getTokens(JavaScenarioParser.TestAnnotation); }
		public TerminalNode TestAnnotation(int i) {
			return getToken(JavaScenarioParser.TestAnnotation, i);
		}
		public List<TerminalNode> TestDeclaration() { return getTokens(JavaScenarioParser.TestDeclaration); }
		public TerminalNode TestDeclaration(int i) {
			return getToken(JavaScenarioParser.TestDeclaration, i);
		}
		public List<ScenarioContext> scenario() {
			return getRuleContexts(ScenarioContext.class);
		}
		public ScenarioContext scenario(int i) {
			return getRuleContext(ScenarioContext.class,i);
		}
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterTest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitTest(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		TestContext _localctx = new TestContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_test);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35);
				match(TestAnnotation);
				setState(36);
				match(TestDeclaration);
				setState(37);
				scenario();
				}
				}
				setState(40); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TestAnnotation );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScenarioContext extends ParserRuleContext {
		public List<TerminalNode> RCURLY() { return getTokens(JavaScenarioParser.RCURLY); }
		public TerminalNode RCURLY(int i) {
			return getToken(JavaScenarioParser.RCURLY, i);
		}
		public List<AsmDeclarationContext> asmDeclaration() {
			return getRuleContexts(AsmDeclarationContext.class);
		}
		public AsmDeclarationContext asmDeclaration(int i) {
			return getRuleContext(AsmDeclarationContext.class,i);
		}
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public List<SetFunctionContext> setFunction() {
			return getRuleContexts(SetFunctionContext.class);
		}
		public SetFunctionContext setFunction(int i) {
			return getRuleContext(SetFunctionContext.class,i);
		}
		public List<StepFunctionContext> stepFunction() {
			return getRuleContexts(StepFunctionContext.class);
		}
		public StepFunctionContext stepFunction(int i) {
			return getRuleContext(StepFunctionContext.class,i);
		}
		public List<AssertEqualsContext> assertEquals() {
			return getRuleContexts(AssertEqualsContext.class);
		}
		public AssertEqualsContext assertEquals(int i) {
			return getRuleContext(AssertEqualsContext.class,i);
		}
		public List<TrycatchblockContext> trycatchblock() {
			return getRuleContexts(TrycatchblockContext.class);
		}
		public TrycatchblockContext trycatchblock(int i) {
			return getRuleContext(TrycatchblockContext.class,i);
		}
		public ScenarioContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scenario; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterScenario(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitScenario(this);
		}
	}

	public final ScenarioContext scenario() throws RecognitionException {
		ScenarioContext _localctx = new ScenarioContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_scenario);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << LINE_COMMENT) | (1L << ClassDeclaration) | (1L << TestAnnotation) | (1L << TestDeclaration) | (1L << SetFunc) | (1L << StepFunc) | (1L << Getter) | (1L << TryCatch) | (1L << Identifier) | (1L << STRING) | (1L << ASMID) | (1L << ASSERT_EQUALS) | (1L << AND) | (1L << OR) | (1L << NOT) | (1L << EQ) | (1L << COMMA) | (1L << COLONS) | (1L << SEMI) | (1L << LPAREN) | (1L << RPAREN) | (1L << LCURLY) | (1L << DOT) | (1L << START) | (1L << AT) | (1L << DOUBLE_QUOTES) | (1L << NEW) | (1L << SET) | (1L << STEP) | (1L << GET) | (1L << TRY) | (1L << CATCH) | (1L << INT) | (1L << ID) | (1L << WS))) != 0)) {
				{
				setState(49);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(42);
					asmDeclaration();
					}
					break;
				case 2:
					{
					setState(43);
					variableDeclaration();
					}
					break;
				case 3:
					{
					setState(44);
					setFunction();
					}
					break;
				case 4:
					{
					setState(45);
					stepFunction();
					}
					break;
				case 5:
					{
					setState(46);
					assertEquals();
					}
					break;
				case 6:
					{
					setState(47);
					trycatchblock();
					}
					break;
				case 7:
					{
					setState(48);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==RCURLY) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(54);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AsmDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> ASMID() { return getTokens(JavaScenarioParser.ASMID); }
		public TerminalNode ASMID(int i) {
			return getToken(JavaScenarioParser.ASMID, i);
		}
		public TerminalNode ID() { return getToken(JavaScenarioParser.ID, 0); }
		public TerminalNode EQ() { return getToken(JavaScenarioParser.EQ, 0); }
		public TerminalNode NEW() { return getToken(JavaScenarioParser.NEW, 0); }
		public TerminalNode LPAREN() { return getToken(JavaScenarioParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaScenarioParser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(JavaScenarioParser.SEMI, 0); }
		public AsmDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asmDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterAsmDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitAsmDeclaration(this);
		}
	}

	public final AsmDeclarationContext asmDeclaration() throws RecognitionException {
		AsmDeclarationContext _localctx = new AsmDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_asmDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(ASMID);
			setState(57);
			match(ID);
			setState(58);
			match(EQ);
			setState(59);
			match(NEW);
			setState(60);
			match(ASMID);
			setState(61);
			match(LPAREN);
			setState(62);
			match(RPAREN);
			setState(63);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public VariableTypeContext variableType() {
			return getRuleContext(VariableTypeContext.class,0);
		}
		public VariableNameContext variableName() {
			return getRuleContext(VariableNameContext.class,0);
		}
		public TerminalNode EQ() { return getToken(JavaScenarioParser.EQ, 0); }
		public VariableValueContext variableValue() {
			return getRuleContext(VariableValueContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaScenarioParser.SEMI, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitVariableDeclaration(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			variableType();
			setState(66);
			variableName();
			setState(67);
			match(EQ);
			setState(68);
			variableValue();
			setState(69);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaScenarioParser.Identifier, 0); }
		public TerminalNode ID() { return getToken(JavaScenarioParser.ID, 0); }
		public VariableTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterVariableType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitVariableType(this);
		}
	}

	public final VariableTypeContext variableType() throws RecognitionException {
		VariableTypeContext _localctx = new VariableTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variableType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_la = _input.LA(1);
			if ( !(_la==Identifier || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JavaScenarioParser.ID, 0); }
		public VariableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterVariableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitVariableName(this);
		}
	}

	public final VariableNameContext variableName() throws RecognitionException {
		VariableNameContext _localctx = new VariableNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableValueContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaScenarioParser.Identifier, 0); }
		public TerminalNode Getter() { return getToken(JavaScenarioParser.Getter, 0); }
		public VariableValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterVariableValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitVariableValue(this);
		}
	}

	public final VariableValueContext variableValue() throws RecognitionException {
		VariableValueContext _localctx = new VariableValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_variableValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_la = _input.LA(1);
			if ( !(_la==Getter || _la==Identifier) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertEqualsContext extends ParserRuleContext {
		public TerminalNode ASSERT_EQUALS() { return getToken(JavaScenarioParser.ASSERT_EQUALS, 0); }
		public TerminalNode LPAREN() { return getToken(JavaScenarioParser.LPAREN, 0); }
		public ActualContext actual() {
			return getRuleContext(ActualContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(JavaScenarioParser.COMMA, 0); }
		public ExpectedContext expected() {
			return getRuleContext(ExpectedContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaScenarioParser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(JavaScenarioParser.SEMI, 0); }
		public AssertEqualsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertEquals; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterAssertEquals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitAssertEquals(this);
		}
	}

	public final AssertEqualsContext assertEquals() throws RecognitionException {
		AssertEqualsContext _localctx = new AssertEqualsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assertEquals);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(ASSERT_EQUALS);
			setState(78);
			match(LPAREN);
			setState(79);
			actual();
			setState(80);
			match(COMMA);
			setState(81);
			expected();
			setState(82);
			match(RPAREN);
			setState(83);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActualContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaScenarioParser.Identifier, 0); }
		public TerminalNode STRING() { return getToken(JavaScenarioParser.STRING, 0); }
		public List<TerminalNode> INT() { return getTokens(JavaScenarioParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(JavaScenarioParser.INT, i);
		}
		public ActualContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actual; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterActual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitActual(this);
		}
	}

	public final ActualContext actual() throws RecognitionException {
		ActualContext _localctx = new ActualContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_actual);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(85);
				match(Identifier);
				}
				break;
			case INT:
				{
				setState(87); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(86);
					match(INT);
					}
					}
					setState(89); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INT );
				}
				break;
			case STRING:
				{
				setState(91);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpectedContext extends ParserRuleContext {
		public TerminalNode Getter() { return getToken(JavaScenarioParser.Getter, 0); }
		public TerminalNode ID() { return getToken(JavaScenarioParser.ID, 0); }
		public ExpectedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expected; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterExpected(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitExpected(this);
		}
	}

	public final ExpectedContext expected() throws RecognitionException {
		ExpectedContext _localctx = new ExpectedContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expected);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_la = _input.LA(1);
			if ( !(_la==Getter || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetFunctionContext extends ParserRuleContext {
		public TerminalNode SetFunc() { return getToken(JavaScenarioParser.SetFunc, 0); }
		public TerminalNode LPAREN() { return getToken(JavaScenarioParser.LPAREN, 0); }
		public SetVariableValueContext setVariableValue() {
			return getRuleContext(SetVariableValueContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaScenarioParser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(JavaScenarioParser.SEMI, 0); }
		public SetFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterSetFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitSetFunction(this);
		}
	}

	public final SetFunctionContext setFunction() throws RecognitionException {
		SetFunctionContext _localctx = new SetFunctionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_setFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(SetFunc);
			setState(97);
			match(LPAREN);
			setState(98);
			setVariableValue();
			setState(99);
			match(RPAREN);
			setState(100);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StepFunctionContext extends ParserRuleContext {
		public TerminalNode StepFunc() { return getToken(JavaScenarioParser.StepFunc, 0); }
		public TerminalNode LPAREN() { return getToken(JavaScenarioParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaScenarioParser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(JavaScenarioParser.SEMI, 0); }
		public StepFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stepFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterStepFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitStepFunction(this);
		}
	}

	public final StepFunctionContext stepFunction() throws RecognitionException {
		StepFunctionContext _localctx = new StepFunctionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_stepFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(StepFunc);
			setState(103);
			match(LPAREN);
			setState(104);
			match(RPAREN);
			setState(105);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetVariableValueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JavaScenarioParser.ID, 0); }
		public TerminalNode STRING() { return getToken(JavaScenarioParser.STRING, 0); }
		public List<TerminalNode> INT() { return getTokens(JavaScenarioParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(JavaScenarioParser.INT, i);
		}
		public SetVariableValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setVariableValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterSetVariableValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitSetVariableValue(this);
		}
	}

	public final SetVariableValueContext setVariableValue() throws RecognitionException {
		SetVariableValueContext _localctx = new SetVariableValueContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_setVariableValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(107);
				match(ID);
				}
				break;
			case STRING:
				{
				setState(108);
				match(STRING);
				}
				break;
			case INT:
				{
				setState(110); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(109);
					match(INT);
					}
					}
					setState(112); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INT );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TrycatchblockContext extends ParserRuleContext {
		public TerminalNode TRY() { return getToken(JavaScenarioParser.TRY, 0); }
		public List<TerminalNode> LCURLY() { return getTokens(JavaScenarioParser.LCURLY); }
		public TerminalNode LCURLY(int i) {
			return getToken(JavaScenarioParser.LCURLY, i);
		}
		public List<TerminalNode> RCURLY() { return getTokens(JavaScenarioParser.RCURLY); }
		public TerminalNode RCURLY(int i) {
			return getToken(JavaScenarioParser.RCURLY, i);
		}
		public TerminalNode CATCH() { return getToken(JavaScenarioParser.CATCH, 0); }
		public TerminalNode LPAREN() { return getToken(JavaScenarioParser.LPAREN, 0); }
		public TrycatchblockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trycatchblock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).enterTrycatchblock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaScenarioListener ) ((JavaScenarioListener)listener).exitTrycatchblock(this);
		}
	}

	public final TrycatchblockContext trycatchblock() throws RecognitionException {
		TrycatchblockContext _localctx = new TrycatchblockContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_trycatchblock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(TRY);
			setState(117);
			match(LCURLY);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(118);
					matchWildcard();
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(124);
			match(RCURLY);
			setState(125);
			match(CATCH);
			setState(126);
			match(LPAREN);
			setState(130);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(127);
					matchWildcard();
					}
					} 
				}
				setState(132);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(133);
			match(LCURLY);
			setState(137);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(134);
					matchWildcard();
					}
					} 
				}
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(140);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\u0091\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\6\3)\n\3\r\3\16\3*\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\64\n\4"+
		"\f\4\16\4\67\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\6\13Z\n\13\r\13\16\13[\3\13\5\13_\n\13\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\6\17q\n\17\r\17"+
		"\16\17r\5\17u\n\17\3\20\3\20\3\20\7\20z\n\20\f\20\16\20}\13\20\3\20\3"+
		"\20\3\20\3\20\7\20\u0083\n\20\f\20\16\20\u0086\13\20\3\20\3\20\7\20\u008a"+
		"\n\20\f\20\16\20\u008d\13\20\3\20\3\20\3\20\5{\u0084\u008b\2\21\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36\2\6\3\2\32\32\4\2\f\f&&\4\2\n\n\f\f"+
		"\4\2\n\n&&\2\u0092\2 \3\2\2\2\4(\3\2\2\2\6\65\3\2\2\2\b:\3\2\2\2\nC\3"+
		"\2\2\2\fI\3\2\2\2\16K\3\2\2\2\20M\3\2\2\2\22O\3\2\2\2\24^\3\2\2\2\26`"+
		"\3\2\2\2\30b\3\2\2\2\32h\3\2\2\2\34t\3\2\2\2\36v\3\2\2\2 !\7\5\2\2!\""+
		"\5\4\3\2\"#\7\32\2\2#$\7\2\2\3$\3\3\2\2\2%&\7\6\2\2&\'\7\7\2\2\')\5\6"+
		"\4\2(%\3\2\2\2)*\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\5\3\2\2\2,\64\5\b\5\2-\64"+
		"\5\n\6\2.\64\5\30\r\2/\64\5\32\16\2\60\64\5\22\n\2\61\64\5\36\20\2\62"+
		"\64\n\2\2\2\63,\3\2\2\2\63-\3\2\2\2\63.\3\2\2\2\63/\3\2\2\2\63\60\3\2"+
		"\2\2\63\61\3\2\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2"+
		"\2\2\668\3\2\2\2\67\65\3\2\2\289\7\32\2\29\7\3\2\2\2:;\7\16\2\2;<\7&\2"+
		"\2<=\7\23\2\2=>\7\37\2\2>?\7\16\2\2?@\7\27\2\2@A\7\30\2\2AB\7\26\2\2B"+
		"\t\3\2\2\2CD\5\f\7\2DE\5\16\b\2EF\7\23\2\2FG\5\20\t\2GH\7\26\2\2H\13\3"+
		"\2\2\2IJ\t\3\2\2J\r\3\2\2\2KL\7&\2\2L\17\3\2\2\2MN\t\4\2\2N\21\3\2\2\2"+
		"OP\7\17\2\2PQ\7\27\2\2QR\5\24\13\2RS\7\24\2\2ST\5\26\f\2TU\7\30\2\2UV"+
		"\7\26\2\2V\23\3\2\2\2W_\7\f\2\2XZ\7%\2\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2"+
		"[\\\3\2\2\2\\_\3\2\2\2]_\7\r\2\2^W\3\2\2\2^Y\3\2\2\2^]\3\2\2\2_\25\3\2"+
		"\2\2`a\t\5\2\2a\27\3\2\2\2bc\7\b\2\2cd\7\27\2\2de\5\34\17\2ef\7\30\2\2"+
		"fg\7\26\2\2g\31\3\2\2\2hi\7\t\2\2ij\7\27\2\2jk\7\30\2\2kl\7\26\2\2l\33"+
		"\3\2\2\2mu\7&\2\2nu\7\r\2\2oq\7%\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3"+
		"\2\2\2su\3\2\2\2tm\3\2\2\2tn\3\2\2\2tp\3\2\2\2u\35\3\2\2\2vw\7#\2\2w{"+
		"\7\31\2\2xz\13\2\2\2yx\3\2\2\2z}\3\2\2\2{|\3\2\2\2{y\3\2\2\2|~\3\2\2\2"+
		"}{\3\2\2\2~\177\7\32\2\2\177\u0080\7$\2\2\u0080\u0084\7\27\2\2\u0081\u0083"+
		"\13\2\2\2\u0082\u0081\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0085\3\2\2\2"+
		"\u0084\u0082\3\2\2\2\u0085\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u008b"+
		"\7\31\2\2\u0088\u008a\13\2\2\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2\2\2"+
		"\u008b\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u008f\7\32\2\2\u008f\37\3\2\2\2\f*\63\65[^rt{\u0084\u008b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}