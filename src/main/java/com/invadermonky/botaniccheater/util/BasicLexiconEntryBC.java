package com.invadermonky.botaniccheater.util;

import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.common.lexicon.BasicLexiconEntry;

public class BasicLexiconEntryBC extends BasicLexiconEntry implements IAddonEntry {
    public BasicLexiconEntryBC(String unlocalizedName, LexiconCategory category) {
        super(unlocalizedName, category);
    }

    @Override
    public String getSubtitle() {
        return "[Botanic Cheater]";
    }
}
