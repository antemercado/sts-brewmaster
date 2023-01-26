package theBrewmaster.stances;

import theBrewmaster.BrewmasterMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;


public class IntoxicatedStance extends AbstractStance{
    public static final String STANCE_ID = BrewmasterMod.makeID("Intoxicated");
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceString.NAME;
    public static final String[] DESCRIPTIONS = stanceString.DESCRIPTION;

    public IntoxicatedStance() {
        ID = STANCE_ID;
        name = NAME;
        updateDescription();
    }

    public void onEnterStance() {}

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    
}
