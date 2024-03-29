/* ExpressionParserTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. ExpressionParserTokenManager.java */
package symbolic;

import java.io.*;

/** Token Manager. */
@SuppressWarnings("unused")
public class ExpressionParserTokenManager implements ExpressionParserConstants {

	/** Debug output. */
	public static java.io.PrintStream debugStream = System.out;

	/** Set debug output. */
	public static void setDebugStream(java.io.PrintStream ds) {
		debugStream = ds;
	}

	private static final int jjStopStringLiteralDfa_0(int pos, long active0) {
		switch (pos) {
		case 0:
			if ((active0 & 0x1000L) != 0L) {
				jjmatchedKind = 13;
				return 7;
			}
			return -1;
		default:
			return -1;
		}
	}

	private static final int jjStartNfa_0(int pos, long active0) {
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}

	static private int jjStopAtPos(int pos, int kind) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	static private int jjMoveStringLiteralDfa0_0() {
		switch (curChar) {
		case 10:
			return jjStopAtPos(0, 1);
		case 13:
			return jjStopAtPos(0, 2);
		case 40:
			return jjStopAtPos(0, 8);
		case 41:
			return jjStopAtPos(0, 9);
		case 42:
			return jjStopAtPos(0, 5);
		case 43:
			return jjStopAtPos(0, 3);
		case 45:
			return jjStopAtPos(0, 4);
		case 47:
			return jjStopAtPos(0, 6);
		case 94:
			return jjStopAtPos(0, 7);
		case 112:
			return jjMoveStringLiteralDfa1_0(0x1000L);
		default:
			return jjMoveNfa_0(2, 0);
		}
	}

	static private int jjMoveStringLiteralDfa1_0(long active0) {
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}
		switch (curChar) {
		case 105:
			if ((active0 & 0x1000L) != 0L)
				return jjStartNfaWithStates_0(1, 12, 7);
			break;
		default:
			break;
		}
		return jjStartNfa_0(0, active0);
	}

	static private int jjStartNfaWithStates_0(int pos, int kind, int state) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			return pos + 1;
		}
		return jjMoveNfa_0(state, pos + 1);
	}

	static private int jjMoveNfa_0(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 47;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (;;) {
			if (++jjround == 0x7fffffff)
				ReInitRounds();
			if (curChar < 64) {
				long l = 1L << curChar;
				do {
					switch (jjstateSet[--i]) {
					case 2:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 16)
							kind = 16; {
						jjCheckNAddStates(0, 2);
					}
						break;
					case 7:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 13)
							kind = 13;
						jjstateSet[jjnewStateCnt++] = 7;
						break;
					case 43:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 16)
							kind = 16; {
						jjCheckNAdd(43);
					}
						break;
					case 44:
						if ((0x3ff000000000000L & l) != 0L) {
							jjCheckNAddTwoStates(44, 45);
						}
						break;
					case 45:
						if (curChar == 46) {
							jjCheckNAdd(46);
						}
						break;
					case 46:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 17)
							kind = 17; {
						jjCheckNAdd(46);
					}
						break;
					default:
						break;
					}
				} while (i != startsAt);
			} else if (curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
					case 2:
						if ((0x7fffffe07fffffeL & l) != 0L) {
							if (kind > 13)
								kind = 13;
							{
								jjCheckNAdd(7);
							}
						}
						if (curChar == 97) {
							jjAddStates(3, 8);
						} else if (curChar == 116) {
							jjAddStates(9, 10);
						} else if (curChar == 99) {
							jjAddStates(11, 12);
						} else if (curChar == 115) {
							jjAddStates(13, 15);
						} else if (curChar == 108)
							jjstateSet[jjnewStateCnt++] = 4;
						else if (curChar == 101)
							jjstateSet[jjnewStateCnt++] = 1;
						break;
					case 0:
						if (curChar == 112 && kind > 11)
							kind = 11;
						break;
					case 1:
						if (curChar == 120)
							jjstateSet[jjnewStateCnt++] = 0;
						break;
					case 3:
						if (curChar == 103 && kind > 11)
							kind = 11;
						break;
					case 4:
						if (curChar == 111)
							jjstateSet[jjnewStateCnt++] = 3;
						break;
					case 5:
						if (curChar == 108)
							jjstateSet[jjnewStateCnt++] = 4;
						break;
					case 6:
					case 7:
						if ((0x7fffffe07fffffeL & l) == 0L)
							break;
						if (kind > 13)
							kind = 13; {
						jjCheckNAdd(7);
					}
						break;
					case 8:
						if (curChar == 115) {
							jjAddStates(13, 15);
						}
						break;
					case 9:
						if (curChar == 110 && kind > 11)
							kind = 11;
						break;
					case 10:
					case 27:
						if (curChar == 105) {
							jjCheckNAdd(9);
						}
						break;
					case 11:
						if (curChar == 104 && kind > 11)
							kind = 11;
						break;
					case 12:
					case 24:
					case 33:
					case 39:
						if (curChar == 110) {
							jjCheckNAdd(11);
						}
						break;
					case 13:
						if (curChar == 105)
							jjstateSet[jjnewStateCnt++] = 12;
						break;
					case 14:
						if (curChar == 116 && kind > 11)
							kind = 11;
						break;
					case 15:
						if (curChar == 114)
							jjstateSet[jjnewStateCnt++] = 14;
						break;
					case 16:
						if (curChar == 113)
							jjstateSet[jjnewStateCnt++] = 15;
						break;
					case 17:
						if (curChar == 99) {
							jjAddStates(11, 12);
						}
						break;
					case 18:
						if (curChar == 115 && kind > 11)
							kind = 11;
						break;
					case 19:
					case 29:
						if (curChar == 111) {
							jjCheckNAdd(18);
						}
						break;
					case 20:
					case 36:
						if (curChar == 115) {
							jjCheckNAdd(11);
						}
						break;
					case 21:
						if (curChar == 111)
							jjstateSet[jjnewStateCnt++] = 20;
						break;
					case 22:
						if (curChar == 116) {
							jjAddStates(9, 10);
						}
						break;
					case 23:
					case 31:
						if (curChar == 97) {
							jjCheckNAdd(9);
						}
						break;
					case 25:
						if (curChar == 97)
							jjstateSet[jjnewStateCnt++] = 24;
						break;
					case 26:
						if (curChar == 97) {
							jjAddStates(3, 8);
						}
						break;
					case 28:
						if (curChar == 115)
							jjstateSet[jjnewStateCnt++] = 27;
						break;
					case 30:
						if (curChar == 99)
							jjstateSet[jjnewStateCnt++] = 29;
						break;
					case 32:
						if (curChar == 116)
							jjstateSet[jjnewStateCnt++] = 31;
						break;
					case 34:
						if (curChar == 105)
							jjstateSet[jjnewStateCnt++] = 33;
						break;
					case 35:
						if (curChar == 115)
							jjstateSet[jjnewStateCnt++] = 34;
						break;
					case 37:
						if (curChar == 111)
							jjstateSet[jjnewStateCnt++] = 36;
						break;
					case 38:
						if (curChar == 99)
							jjstateSet[jjnewStateCnt++] = 37;
						break;
					case 40:
						if (curChar == 97)
							jjstateSet[jjnewStateCnt++] = 39;
						break;
					case 41:
						if (curChar == 116)
							jjstateSet[jjnewStateCnt++] = 40;
						break;
					default:
						break;
					}
				} while (i != startsAt);
			} else {
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
					default:
						break;
					}
				} while (i != startsAt);
			}
			if (kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 47 - (jjnewStateCnt = startsAt)))
				return curPos;
			try {
				curChar = input_stream.readChar();
			} catch (java.io.IOException e) {
				return curPos;
			}
		}
	}

	/** Token literal values. */
	public static final String[] jjstrLiteralImages = { "", "\12", "\15", "\53", "\55", "\52", "\57", "\136", "\50",
			"\51", null, null, "\160\151", null, null, null, null, null, };

	static protected Token jjFillToken() {
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		String im = jjstrLiteralImages[jjmatchedKind];
		curTokenImage = (im == null) ? input_stream.GetImage() : im;
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = Token.newToken(jjmatchedKind);
		t.kind = jjmatchedKind;
		t.image = curTokenImage;

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}

	static final int[] jjnextStates = { 43, 44, 45, 28, 30, 32, 35, 38, 41, 23, 25, 19, 21, 10, 13, 16, };

	static int curLexState = 0;
	static int defaultLexState = 0;
	static int jjnewStateCnt;
	static int jjround;
	static int jjmatchedPos;
	static int jjmatchedKind;

	/** Get the next Token. */
	public static Token getNextToken() {
		Token matchedToken;
		int curPos = 0;

		EOFLoop: for (;;) {
			try {
				curChar = input_stream.BeginToken();
			} catch (Exception e) {
				jjmatchedKind = 0;
				jjmatchedPos = -1;
				matchedToken = jjFillToken();
				return matchedToken;
			}

			try {
				input_stream.backup(0);
				while (curChar <= 32 && (0x100000000L & (1L << curChar)) != 0L)
					curChar = input_stream.BeginToken();
			} catch (java.io.IOException e1) {
				continue EOFLoop;
			}
			jjmatchedKind = 0x7fffffff;
			jjmatchedPos = 0;
			curPos = jjMoveStringLiteralDfa0_0();
			if (jjmatchedKind != 0x7fffffff) {
				if (jjmatchedPos + 1 < curPos)
					input_stream.backup(curPos - jjmatchedPos - 1);
				if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
					matchedToken = jjFillToken();
					return matchedToken;
				} else {
					continue EOFLoop;
				}
			}
			int error_line = input_stream.getEndLine();
			int error_column = input_stream.getEndColumn();
			String error_after = null;
			boolean EOFSeen = false;
			try {
				input_stream.readChar();
				input_stream.backup(1);
			} catch (java.io.IOException e1) {
				EOFSeen = true;
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
				if (curChar == '\n' || curChar == '\r') {
					error_line++;
					error_column = 0;
				} else
					error_column++;
			}
			if (!EOFSeen) {
				input_stream.backup(1);
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
			}
			throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar,
					TokenMgrError.LEXICAL_ERROR);
		}
	}

	static void SkipLexicalActions(Token matchedToken) {
		switch (jjmatchedKind) {
		default:
			break;
		}
	}

	static void MoreLexicalActions() {
		jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
		switch (jjmatchedKind) {
		default:
			break;
		}
	}

	static void TokenLexicalActions(Token matchedToken) {
		switch (jjmatchedKind) {
		default:
			break;
		}
	}

	static private void jjCheckNAdd(int state) {
		if (jjrounds[state] != jjround) {
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}

	static private void jjAddStates(int start, int end) {
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		} while (start++ != end);
	}

	static private void jjCheckNAddTwoStates(int state1, int state2) {
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}

	static private void jjCheckNAddStates(int start, int end) {
		do {
			jjCheckNAdd(jjnextStates[start]);
		} while (start++ != end);
	}

	/** Constructor. */
	public ExpressionParserTokenManager(SimpleCharStream stream) {

		if (input_stream != null)
			throw new TokenMgrError(
					"ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.",
					TokenMgrError.STATIC_LEXER_ERROR);

		input_stream = stream;
	}

	/** Constructor. */
	public ExpressionParserTokenManager(SimpleCharStream stream, int lexState) {
		ReInit(stream);
		SwitchTo(lexState);
	}

	/** Reinitialise parser. */

	static public void ReInit(SimpleCharStream stream) {

		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}

	static private void ReInitRounds() {
		int i;
		jjround = 0x80000001;
		for (i = 47; i-- > 0;)
			jjrounds[i] = 0x80000000;
	}

	/** Reinitialise parser. */
	static public void ReInit(SimpleCharStream stream, int lexState)

	{
		ReInit(stream);
		SwitchTo(lexState);
	}

	/** Switch to specified lex state. */
	public static void SwitchTo(int lexState) {
		if (lexState >= 1 || lexState < 0)
			throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.",
					TokenMgrError.INVALID_LEXICAL_STATE);
		else
			curLexState = lexState;
	}

	/** Lexer state names. */
	public static final String[] lexStateNames = { "DEFAULT", };

	/** Lex State array. */
	public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, };
	static final long[] jjtoToken = { 0x33bffL, };
	static final long[] jjtoSkip = { 0x400L, };
	static final long[] jjtoSpecial = { 0x0L, };
	static final long[] jjtoMore = { 0x0L, };
	static protected SimpleCharStream input_stream;

	static private final int[] jjrounds = new int[47];
	static private final int[] jjstateSet = new int[2 * 47];
	private static final StringBuilder jjimage = new StringBuilder();
	private static StringBuilder image = jjimage;
	private static int jjimageLen;
	private static int lengthOfMatch;
	static protected int curChar;
}
