package theBrewmaster.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class HomebrewAction extends AbstractGameAction{

    private int miscChange;
    private UUID uuid;
    private boolean reset;

    public HomebrewAction(UUID targetUUID, int miscIncrease) {
        this.miscChange = miscIncrease;
        this.uuid = targetUUID;
        this.reset = false;
    }

    public HomebrewAction(UUID targetUUID, int miscValue, boolean reset) {
        this.uuid = targetUUID;
        this.miscChange = miscValue;
        this.reset = reset;
    }

    @Override
    public void update() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            if (reset) 
                c.misc = miscChange;
            else
                c.misc += miscChange;
            c.applyPowers();
            c.baseMagicNumber = c.misc;
            c.isMagicNumberModified = false;
        }
        for (AbstractCard c: GetAllInBattleInstances.get(this.uuid)) {
            if (reset) 
                c.misc = miscChange;
            else 
                c.misc += miscChange;
            c.applyPowers();
            c.baseMagicNumber = c.misc;
        }
        this.isDone = true;
        
    }
    
}
