package planning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithms.set.CartesianProduct;

public class IdsMatchingManager {

	private List<IdsMatching> idsMatchings = new ArrayList<IdsMatching>();

	private IdsMatching unchekedIdsMatching = null;

	public IdsMatching getUncheckedMatching() {
		return unchekedIdsMatching;
	}

	public boolean haveUncheckedMatching() {
		if (unchekedIdsMatching != null) {
			unchekedIdsMatching.check();

		}
		for (IdsMatching idsMatching : idsMatchings) {
			if (!idsMatching.isChecked()) {
				unchekedIdsMatching = idsMatching;
				return true;
			}
		}
		unchekedIdsMatching = null;
		return false;
	}

	public void removeMatching(IdsMatching idsMatching) {
		idsMatchings.remove(idsMatching);
	}

	public int getMatchingsAmount() {
		return idsMatchings.size();
	}

	public IdsMatching getMatching(int index) {
		return idsMatchings.get(index);
	}

	private Map<String, List<String>> candidates = new HashMap<String, List<String>>();

	public void prepareMatchingsCandidates(Set<String> templateIds, Set<String> systemIds) {
		candidates.clear();
		for (String templateId : templateIds) {
			candidates.put(templateId, new ArrayList<>(systemIds));
		}
	}

	public void removeMatchingsCandidate(String templateId, String objectId) {
		List<String> systemIds = candidates.get(templateId);
		systemIds.remove(objectId);
	}

	public void generateMatchingsFromCandidates() {
		List<List<String>> objectIdsCombinations = CartesianProduct.compute(candidates.values());
		List<String> templateIdsCombination = new ArrayList<String>(candidates.keySet());
		for (List<String> objectIdsCombination : objectIdsCombinations) {
			Set<String> uniqueObjectIds = new HashSet<String>();
			uniqueObjectIds.addAll(objectIdsCombination);
			if (uniqueObjectIds.size() == objectIdsCombination.size()) {
				IdsMatching idsMatching = new IdsMatching();

				for (int i = 0; i < objectIdsCombination.size(); i++) {
					idsMatching.add(templateIdsCombination.get(i), objectIdsCombination.get(i));
				}
				idsMatchings.add(idsMatching);
			}
		}
	}
}