package asmeta.ai.propgen.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import asmeta.ai.propgen.PropertyType;
import asmeta.ai.propgen.util.AsmetaAIContentInserter.InsertionEdit;

class TestAsmetaAIContentInserter {

	@Test
	void insertAsmToNlAddsCommentsAboveMainRule() {
		String model = "asm Clock\n\nsignature:\n\ndefinitions:\n\n\tmain rule r_Main =\n\t\tskip\n";

		InsertionEdit edit = AsmetaAIContentInserter.asmToNlEdit(model, "seconds increase\nminutes update");
		String result = apply(model, edit);

		String expected = "asm Clock\n\nsignature:\n\ndefinitions:\n\n"
				+ "\t// AIGEN: seconds increase\n"
				+ "\t// AIGEN: minutes update\n\n"
				+ "\tmain rule r_Main =\n\t\tskip\n";
		assertEquals(expected, result);
	}

	@Test
	void insertTlToNlAddsCommentAboveSelectedProperty() {
		String model = "definitions:\n\nCTLSPEC ag(flag)\nmain rule r_Main =\\n\\t\\tskip\\n";

		InsertionEdit edit = AsmetaAIContentInserter.tlToNlEdit(model, model.indexOf("CTLSPEC"), "flag always holds");
		String result = apply(model, edit);

		assertEquals("definitions:\n\n// AIGEN: flag always holds\nCTLSPEC ag(flag)\nmain rule r_Main =\\n\\t\\tskip\\n", result);
	}

	@Test
	void insertNlToTlAddsCommentAndPropertyBelowSelection() {
		String model = "// flag always holds\n";

		InsertionEdit edit = AsmetaAIContentInserter.nlToTlEdit(model, model.indexOf("flag"),
				"flag always holds".length(), "CTLSPEC ag(flag)", PropertyType.CTLPROP);
		String result = apply(model, edit);

		assertEquals("// flag always holds\n// AIGEN: CTL property\nCTLSPEC ag(flag)\n", result);
	}

	private String apply(String text, InsertionEdit edit) {
		return text.substring(0, edit.offset) + edit.text + text.substring(edit.offset);
	}

}
