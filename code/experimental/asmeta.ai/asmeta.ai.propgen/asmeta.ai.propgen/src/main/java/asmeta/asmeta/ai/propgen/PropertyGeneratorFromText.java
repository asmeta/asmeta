package asmeta.asmeta.ai.propgen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.asmeta.parser.util.AsmPrinter;

import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

import asmeta.structure.Asm;

/**
 * 
 * Given an ASM and a natural language description of a property, this class is
 * used to generate the corresponding temporal property.
 * 
 */
public class PropertyGeneratorFromText extends PropertyGenerator {

	/**
	 * Constructor
	 * 
	 * @param asm the ASM to be used for generating the property
	 */
	public PropertyGeneratorFromText(Asm asm) {
		this.asm = asm;
	}

	/**
	 * Generates a temporal property from a natural language description.
	 * 
	 * @param text the natural language description of the property
	 * @param type the type of the property to be generated (CTL or LTL)
	 * @throws FileNotFoundException
	 */
	public void generatePropertiesFromText(String text, PropertyType type) throws FileNotFoundException {
		String promptText = "";

		// Simple prompt
		if (type == PropertyType.CTLPROP) {
			promptText = "Encode in CTL an ASMETA property verifying the following: " + text;
		} else if (type == PropertyType.LTLPROP) {
			promptText = "Encode in CTL an ASMETA property verifying the following: " + text;
		}

		// Append to the prompt text the ASMETA specification
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		AsmPrinter printer = new AsmPrinter(printWriter);
		printer.visit(asm);
		promptText += "\n\nHere is the ASMETA specification:\n\n" + stringWriter.toString().replace("\n\n", "\n")
				+ "\n\nDo not include any commentary or multiple options. Just return the temporal property.\n" +
				"When you use logical operators, use the following: or, xor, and, not, implies, iff. Do not use other symbols." ;

		// System.out.println(promptText);

		// Get the response from the OpenAI API
		ResponseCreateParams params = ResponseCreateParams.builder().input(promptText).model(ChatModel.GPT_5_NANO)
				.build();

		// Print the response
		client.responses().create(params).output().stream().flatMap(item -> item.message().stream())
				.flatMap(message -> message.content().stream()).flatMap(content -> content.outputText().stream())
				.forEach(outputText -> System.out.println(outputText.text()));
	}
}
