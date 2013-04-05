/* Generated By:JavaCC: Do not edit this line. ShellParserConstants.java */
package org.xmlsh.sh.grammar;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ShellParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int NEWLINE = 4;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 5;
  /** RegularExpression Id. */
  int CSEMI = 6;
  /** RegularExpression Id. */
  int AND_IF = 7;
  /** RegularExpression Id. */
  int OR_IF = 8;
  /** RegularExpression Id. */
  int DGREAT = 9;
  /** RegularExpression Id. */
  int DSEMI = 10;
  /** RegularExpression Id. */
  int CLOBBER = 11;
  /** RegularExpression Id. */
  int DLESSMINUS = 12;
  /** RegularExpression Id. */
  int DLESS = 13;
  /** RegularExpression Id. */
  int CATCH = 14;
  /** RegularExpression Id. */
  int IF = 15;
  /** RegularExpression Id. */
  int ELIF = 16;
  /** RegularExpression Id. */
  int THEN = 17;
  /** RegularExpression Id. */
  int ELSE = 18;
  /** RegularExpression Id. */
  int FI = 19;
  /** RegularExpression Id. */
  int DO = 20;
  /** RegularExpression Id. */
  int CASE = 21;
  /** RegularExpression Id. */
  int ESAC = 22;
  /** RegularExpression Id. */
  int WHILE = 23;
  /** RegularExpression Id. */
  int UNTIL = 24;
  /** RegularExpression Id. */
  int FOR = 25;
  /** RegularExpression Id. */
  int TRY = 26;
  /** RegularExpression Id. */
  int FINALLY = 27;
  /** RegularExpression Id. */
  int RETURN = 28;
  /** RegularExpression Id. */
  int DONE = 29;
  /** RegularExpression Id. */
  int LBRACE = 30;
  /** RegularExpression Id. */
  int BANG = 31;
  /** RegularExpression Id. */
  int LPAREN = 32;
  /** RegularExpression Id. */
  int LPAREN2 = 33;
  /** RegularExpression Id. */
  int LBRACE2 = 34;
  /** RegularExpression Id. */
  int RBRACE2 = 35;
  /** RegularExpression Id. */
  int RBRACE = 36;
  /** RegularExpression Id. */
  int IN = 37;
  /** RegularExpression Id. */
  int LESS = 38;
  /** RegularExpression Id. */
  int GT = 39;
  /** RegularExpression Id. */
  int TGT = 40;
  /** RegularExpression Id. */
  int TGTGT = 41;
  /** RegularExpression Id. */
  int TGTAND1 = 42;
  /** RegularExpression Id. */
  int GTAND = 43;
  /** RegularExpression Id. */
  int GTAND2 = 44;
  /** RegularExpression Id. */
  int LTAND = 45;
  /** RegularExpression Id. */
  int RPAREN = 46;
  /** RegularExpression Id. */
  int AMP = 47;
  /** RegularExpression Id. */
  int SEMI = 48;
  /** RegularExpression Id. */
  int PIPE = 49;
  /** RegularExpression Id. */
  int BIGQUOTE = 50;
  /** RegularExpression Id. */
  int NAME = 51;
  /** RegularExpression Id. */
  int XEXPR = 52;
  /** RegularExpression Id. */
  int FUNC_CALL_WORD = 53;
  /** RegularExpression Id. */
  int METHOD_CALL_WORD = 54;
  /** RegularExpression Id. */
  int WORD = 55;
  /** RegularExpression Id. */
  int STRING_LITERAL1 = 56;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 57;
  /** RegularExpression Id. */
  int VAR_EXPANSION = 58;
  /** RegularExpression Id. */
  int VAR_SUBPROC = 59;
  /** RegularExpression Id. */
  int VAR_SUBPROC_FILE = 60;
  /** RegularExpression Id. */
  int BACKTICK1 = 61;
  /** RegularExpression Id. */
  int BACKTICK2 = 62;
  /** RegularExpression Id. */
  int ASSIGN_WORD = 63;
  /** RegularExpression Id. */
  int ASSIGN_WORDPE = 64;
  /** RegularExpression Id. */
  int VARNAME = 65;
  /** RegularExpression Id. */
  int FUNC_DECL = 66;
  /** RegularExpression Id. */
  int FNAME = 67;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int CMD = 1;
  /** Lexical state. */
  int NEVER = 2;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "<SINGLE_LINE_COMMENT>",
    "\";\"",
    "\"&&\"",
    "\"||\"",
    "\">>\"",
    "\";;\"",
    "\">|\"",
    "\"<<-\"",
    "\"<<\"",
    "\"catch\"",
    "\"if\"",
    "\"elif\"",
    "\"then\"",
    "\"else\"",
    "\"fi\"",
    "\"do\"",
    "\"case\"",
    "\"esac\"",
    "\"while\"",
    "\"until\"",
    "\"for\"",
    "\"try\"",
    "\"finally\"",
    "\"return\"",
    "\"done\"",
    "\"{\"",
    "\"!\"",
    "\"(\"",
    "\"(\"",
    "\"{\"",
    "\"}\"",
    "\"}\"",
    "\"in\"",
    "\"<\"",
    "\">\"",
    "\"2>\"",
    "\"2>>\"",
    "\"2>&1\"",
    "\">&\"",
    "\"1>&2\"",
    "\"<&\"",
    "\")\"",
    "\"&\"",
    "\";\"",
    "\"|\"",
    "\"<{{\"",
    "<NAME>",
    "\"<[\"",
    "<FUNC_CALL_WORD>",
    "<METHOD_CALL_WORD>",
    "<WORD>",
    "<STRING_LITERAL1>",
    "<STRING_LITERAL2>",
    "<VAR_EXPANSION>",
    "<VAR_SUBPROC>",
    "<VAR_SUBPROC_FILE>",
    "\"`\"",
    "\"`\"",
    "<ASSIGN_WORD>",
    "<ASSIGN_WORDPE>",
    "<VARNAME>",
    "<FUNC_DECL>",
    "<FNAME>",
  };

}
