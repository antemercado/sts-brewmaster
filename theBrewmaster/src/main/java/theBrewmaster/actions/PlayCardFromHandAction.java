package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardFromHandAction extends AbstractGameAction{
    

    private AbstractCard toPlay;
    private AbstractPlayer p;

    public PlayCardFromHandAction(AbstractCard toPlay) {
        this.toPlay = toPlay;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            AbstractMonster target = AbstractDungeon.getRandomMonster();
            this.p.hand.group.remove(this.toPlay);
            AbstractDungeon.getCurrRoom().souls.remove(toPlay);
            this.p.limbo.group.add(toPlay);
            this.toPlay.current_y = -200.0f * Settings.scale;
            this.toPlay.target_x = Settings.WIDTH / 2.0f + 200.0f * Settings.xScale;
            this.toPlay.target_y = Settings.HEIGHT / 2.0f;
            this.toPlay.targetAngle = 0.0f;
            this.toPlay.lighten(false);
            this.toPlay.drawScale = 0.12f;
            this.toPlay.targetDrawScale = 0.75f;
            
            this.toPlay.applyPowers();
            addToTop(new NewQueueCardAction(this.toPlay, target, false, true));
            addToTop(new UnlimboAction(this.toPlay));
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            this.isDone = true;
        }
    }
}
