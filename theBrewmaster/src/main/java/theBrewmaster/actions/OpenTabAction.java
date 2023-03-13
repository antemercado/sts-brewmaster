package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import theBrewmaster.enums.CustomTags;

public class OpenTabAction extends AbstractGameAction{

    private AbstractPlayer p;

    public OpenTabAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.drawPile.isEmpty()){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);

            for (AbstractCard c : this.p.drawPile.group){
                if (c.hasTag(CustomTags.BREW)){
                    tmp.addToRandomSpot(c);
                }
            }

            if (tmp.size() == 0){
                this.isDone = true;
                return;
            }
            AbstractCard c;
            for (int i = 0; i < this.amount; i++){
                if (!tmp.isEmpty()){
                    tmp.shuffle();
                    c = tmp.getBottomCard();
                    if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE){
                        this.p.createHandIsFullDialog();
                    } else {
                        int cardIndex = this.p.drawPile.group.indexOf(c);
                        this.p.drawPile.removeCard(c);
                        this.p.drawPile.addToTop(c);
                        this.addToTop(new OpenTabSubAction(c, this.p.drawPile, cardIndex));
                        this.addToTop(new DrawCardAction(1));
                    }
                }
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
    
}
