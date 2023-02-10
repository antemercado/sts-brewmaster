package theBrewmaster.ui.campfire;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.util.TextureLoader;
import theBrewmaster.vfx.campfire.CampfireRelaxEffect;

import static theBrewmaster.BrewmasterMod.makeUIPath;

public class RelaxOption extends AbstractCampfireOption{

    public static final String ID = BrewmasterMod.makeID(RelaxOption.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String LABEL = TEXT[0];
    public static final String DESCRIPTION = TEXT[1];
    public static final Texture IMG = TextureLoader.getTexture(makeUIPath("campfire/relax.png"));


    public RelaxOption() {
        super();
        this.label = LABEL;
        this.description = DESCRIPTION;
        this.img = IMG;
    }

    public void useOption() {
        AbstractDungeon.effectList.add(new CampfireRelaxEffect());    
    }
    
}
