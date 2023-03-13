package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class OpenTabSubAction extends AbstractGameAction{

    private AbstractCard card;
    private CardGroup group;
    private int index;

    public OpenTabSubAction(AbstractCard card, CardGroup group, int reinsertIndex) {
        this.card = card;
        this.group = group;
        this.index = reinsertIndex;
    }

    @Override
    public void update() {
        if (this.group.group.contains(this.card)){
            this.group.removeCard(this.card);
            this.group.group.add(this.index, this.card);;
        }
        this.isDone = true;
    }
}