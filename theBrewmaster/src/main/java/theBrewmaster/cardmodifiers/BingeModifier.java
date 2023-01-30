package theBrewmaster.cardmodifiers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.sun.tools.javac.code.Attribute.RetentionPolicy;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.AbstractCardModifier.SaveIgnore;
import theBrewmaster.cards.Binge;

@SaveIgnore
public class BingeModifier extends AbstractCardModifier{
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Binge.ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private CardGroup hand = AbstractDungeon.player.hand;

    public BingeModifier() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card){
        return "Ethereal NL " + EXTENDED_DESCRIPTION[0] + " NL Exhaust";
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group){
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, this.hand));
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action){
        card.exhaust = true;
        action.exhaustCard = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BingeModifier();
    }
    
}
