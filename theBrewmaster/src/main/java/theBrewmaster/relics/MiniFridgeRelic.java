package theBrewmaster.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makeRelicOutlinePath;
import static theBrewmaster.BrewmasterMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MiniFridgeRelic extends CustomRelic {

    public static final String ID = BrewmasterMod.makeID("MiniFridgeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public MiniFridgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInHandAction(BrewmasterMod.getMicrobrews().getRandomCard(true)));
        addToBot(new MakeTempCardInHandAction(BrewmasterMod.getMicrobrews().getRandomCard(true)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}