package theBrewmaster.actions;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class ImportedBrewAction extends AbstractGameAction{

    private boolean retrieveCard = false;
    private boolean upgraded;

    public ImportedBrewAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if (!this.retrieveCard){
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null){
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                disCard.current_x = -1000.0f * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE){
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> ret = new ArrayList<>();

        // Generate Hash Map with all character's card pools
        ArrayList<AbstractPlayer> chars = CardCrawlGame.characterManager.getAllCharacters();
        HashMap<CardColor,ArrayList<AbstractCard>> cards = new HashMap<>(); 
        for (AbstractPlayer p: chars){
            CardColor color = p.getCardColor();
            if (p.getCardColor() != AbstractDungeon.player.getCardColor()){
                ArrayList<AbstractCard> tmp = new ArrayList<>();
                cards.put(color, p.getCardPool(tmp));
            }
        }

        // Generte Card Rewards
        while (ret.size() != 3){
            AbstractCard.CardRarity cardRarity;
            
            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 55){
                cardRarity = CardRarity.COMMON;
            } else if (roll < 85) {
                cardRarity = CardRarity.UNCOMMON;
            } else {
                cardRarity = CardRarity.RARE;
            }

            // Determine which character to use.
            int charIndex = AbstractDungeon.cardRandomRng.random(cards.size() - 1);
            Object charKey = cards.keySet().toArray()[charIndex];
            ArrayList<AbstractCard> charCards = cards.get(charKey);
            
            // Populate CardGroup with cards.
            CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c: charCards){
                if (c.rarity == cardRarity && !c.hasTag(CardTags.HEALING) && c.type != CardType.CURSE && c.type != CardType.STATUS && !UnlockTracker.isCardLocked(c.cardID)){
                    anyCard.addToTop(c);
                }
            }
            
            // Return card.
            if (anyCard.size() > 0){
                anyCard.shuffle(AbstractDungeon.cardRandomRng);
                AbstractCard retCard = anyCard.getRandomCard(true);
                if (this.upgraded){
                    retCard.upgrade();
                }
                ret.add(retCard);
            }
            
            // No longer need the character in the HashMap
            cards.remove(charKey);
        }

        return ret;
    }
    
}
