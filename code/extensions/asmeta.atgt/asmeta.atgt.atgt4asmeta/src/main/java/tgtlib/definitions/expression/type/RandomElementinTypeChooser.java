package tgtlib.definitions.expression.type;

import java.util.List;
import java.util.Random;

/**
 * chooses a random element is a type
 * 
 * @author garganti
 *
 */
public class RandomElementinTypeChooser implements TypeVisitorI<String> {

	public static RandomElementinTypeChooser INSTANCE = new RandomElementinTypeChooser();

	Random r;

	private RandomElementinTypeChooser() {
		r = new Random();
	}

	@Override
	public String forBoundType(BoundType boundType) {
		int low = boundType.getLow();
		int high = boundType.getUp();
		assert boundType.getDelta() == null || boundType.getDelta() == 1
				|| boundType.getDelta() == 0 : " delta " + boundType.getDelta()
				+ " not supported yet";
		int i = low + r.nextInt((high - low + 1));
		return Integer.toString(i);
	}

	public String forElementsType(ElementsType enumType) {
		/* take a random value */
		List<EnumConst> enums = enumType.allElements();
		int i = r.nextInt(enums.size());
		return enums.get(i).toString();
	}

	@Override
	public String forIntegerType(IntegerType intType) {
		return Integer.toString(r.nextInt());
	}

	@Override
	public String forEnumType(EnumType enumType) {
		return forElementsType(enumType);
	}

	@Override
	public String forBoolType(BoolType enumType) {
		return forElementsType(enumType);
	}

}
