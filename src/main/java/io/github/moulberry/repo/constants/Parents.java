package io.github.moulberry.repo.constants;

import lombok.Getter;

import java.util.*;

public class Parents {
    @Getter
    Map<String, List<String>> parents;

    Map<String, String> reverseParents = new HashMap<>();

    public Parents(Map<String, List<String>> parents) {
        this.parents = parents;
        for (Map.Entry<String, List<String>> parentings : parents.entrySet()) {
            String parent = parentings.getKey();
            for (String child : parentings.getValue()) {
                reverseParents.put(child, parent);
            }
        }
    }

    public List<String> getChildren(String itemId) {
        return parents.getOrDefault(itemId, new ArrayList<>());
    }

    public Optional<String> getParent(String itemId) {
        return reverseParents.containsKey(itemId)
                ? Optional.of(reverseParents.get(itemId))
                : Optional.empty();
    }

}
