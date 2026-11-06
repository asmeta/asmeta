package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.AsmCollection;



/** Copies the main model, including non-containment terms and TermDT/RuleDT values. */
final class MutationModelCopier {
    private final EcoreUtil.Copier copier = new EcoreUtil.Copier();

    Asm copy(Asm original) {
        copyObjects(reachable(original));
        return (Asm) copier.get(original);
    }

   public AsmCollection copyMutant(AsmCollection original, int ruleIndex, Rule mutatedRule) {

        List<EObject> objects = reachable(original.getMain());

        Set<EObject> seen = Collections.newSetFromMap(new IdentityHashMap<>());

        seen.addAll(objects);

        for (EObject object : reachable(mutatedRule)) {
            if (seen.add(object)) {
                objects.add(object);
            }
        }

        copyObjects(objects);

        Asm mainCopy = (Asm) copier.get(original.getMain());
        Rule ruleCopy = (Rule) copier.get(mutatedRule);

        mainCopy.getBodySection().getRuleDeclaration().get(ruleIndex).setRuleBody(ruleCopy);

        List<Asm> models = new ArrayList<>();

        for (Asm model : original) {
            if (model == original.getMain()) {
                models.add(mainCopy);
            } else {
                models.add(model);
            }
        }

        return new AsmCollection(models);
    }

    private void copyObjects(List<EObject> objects) {
        for (EObject object : objects) {
            if (!copier.containsKey(object)) {
                copier.copy(object);
            }
        }

        copier.copyReferences();

        // Remap object values stored as TermDT/RuleDT attributes.
        for (EObject object : objects) {
            EObject target = copier.get(object);

            for (EAttribute attribute : object.eClass().getEAllAttributes()) {
                if (attribute.isChangeable() && !attribute.isDerived() && object.eIsSet(attribute)) {

                    Object value = object.eGet(attribute);

                    if (attribute.isMany()) {
                        List<Object> values = new ArrayList<>();

                        for (Object item : (List<?>) value) {
                            values.add(remap(item));
                        }

                        target.eSet(attribute, values);
                    } else {
                        target.eSet(attribute, remap(value));
                    }
                }
            }
        }
    }

   public EObject get(EObject original) {
        return copier.get(original);
    }

    private Object remap(Object value) {
        Object result = value;
        if (value instanceof EObject && copier.containsKey(value)) {
            result = copier.get(value);
        }
        return result;
    }

    public static List<EObject> reachable(EObject root) {
        List<EObject> result = new ArrayList<>();
        Set<EObject> seen = Collections.newSetFromMap(new IdentityHashMap<>());
        result.add(root);
        seen.add(root);
        for (int i = 0; i < result.size(); i++) {
            EObject object = result.get(i);
            for (EStructuralFeature feature : object.eClass().getEAllStructuralFeatures()) {
                if (!feature.isDerived() && object.eIsSet(feature)) {
                    Object value = object.eGet(feature);
                    if (feature.isMany()) {
                        for (Object item : (List<?>) value) {
                            add(feature, item, seen, result);
                        }
                    } else {
                        add(feature, value, seen, result);
                    }
                }
            }
        }
        return result;
    }
    

    private static void add(EStructuralFeature feature, Object value,
            Set<EObject> seen, List<EObject> result) {
        boolean containment = feature instanceof EReference
                && ((EReference) feature).isContainment();
        if (value instanceof EObject && (containment || value instanceof Term || value instanceof Rule)) {
            EObject object = (EObject) value;
            if (seen.add(object)) {
                result.add(object);
            }
        }
    }
}
