package com.example.logic;

import java.util.ArrayList;
import java.util.List;

public class SkillTree {
    private SkillNode root;

    public SkillTree(SkillNode root) {
        this.root = root;
    }

    public SkillNode getRoot() {
        return root;
    }

    public void setRoot(SkillNode root) {
        this.root = root;
    }

    public static class SkillNode {
        private String name;
        private List<SkillNode> children;

        public SkillNode(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SkillNode> getChildren() {
            return children;
        }

        public void addChild(SkillNode child) {
            children.add(child);
        }
    }
}