package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import theBrewmaster.BrewmasterMod;

public class PartyFavorsAction extends AbstractGameAction {

    private AbstractPlayer p;
    private boolean freeToPlayOnce;
    private int energy;

    public PartyFavorsAction(int energyOnUse, boolean freeToPlayOnce) {
        this.p = AbstractDungeon.player;
        this.energy = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    @Override
    public void update() {
        if (this.p.hasRelic("Chemical X")){
            this.energy += 2;
            this.p.getRelic("Chemical X").flash();
        }

        for (int i = 0; i < this.energy; i++){
            AbstractCard c = BrewmasterMod.getMicrobrews().getRandomCard(true);
            c.upgrade();
            addToBot(new MakeTempCardInHandAction(c));
        }

        if (!this.freeToPlayOnce)
            this.p.energy.use(EnergyPanel.totalCount);

        this.isDone = true;
    }
    
}
