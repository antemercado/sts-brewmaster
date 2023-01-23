package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StaggerAction extends AbstractGameAction{

    private AbstractMonster m;

    public StaggerAction(AbstractMonster m) {
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.target = m;
        this.m = m;
    }

    @Override
    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >=0) {
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(m));
        }        
    }
    
}
