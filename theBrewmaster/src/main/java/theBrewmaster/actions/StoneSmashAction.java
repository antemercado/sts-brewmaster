package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StoneSmashAction extends AbstractGameAction{

    public StoneSmashAction(AbstractCreature source, AbstractCreature target, int damage) {
        this.source = source;
        this.target = target;
        this.amount = damage;
    }

    @Override
    public void update() {
        if (shouldCancelAction()){
            this.isDone = true;
            return;
        }
        tickDuration();

        if (this.isDone){
            this.target.damage(new DamageInfo(source, amount));
            if (this.target.lastDamageTaken > 0){
                addToBot(new GainBlockAction(this.source, this.target.lastDamageTaken));        
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop(new WaitAction(0.1f));
            }
        }
        this.isDone = true;        
    }
    
}
