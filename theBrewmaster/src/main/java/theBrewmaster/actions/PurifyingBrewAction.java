package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class PurifyingBrewAction extends AbstractGameAction{

    public PurifyingBrewAction(AbstractPlayer p, int amount) {
        
        this.target = p;
        this.source = p;
        this.amount = amount;

    }

    @Override
    public void update() {
        for (AbstractPower po: this.target.powers){
            if (po.type != PowerType.DEBUFF)
                continue;
            po.reducePower(amount);
        }
    }
    
}
