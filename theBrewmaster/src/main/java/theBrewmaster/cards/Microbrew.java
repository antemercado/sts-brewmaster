package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Microbrew extends AbstractDynamicCard {
    public static final Logger logger = LogManager.getLogger(Microbrew.class.getName());
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(Microbrew.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = 0;

    private static final int POWER = 5;
    public AbstractCard newCard = null;

    public Microbrew() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = POWER;

        tags.add(CustomTags.BREW);

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, magicNumber)));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            // This still doesnt work properly. Take another look when you can. It works well enough to test tho.
            logger.info("----------------Starting Microbrew Upgrade-----------------");
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();

            // Find this cards location
            if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
                logger.info("Dungeon and Player exist");

                newCard = BrewmasterCharacter.getBrews().getRandomCard(AbstractDungeon.cardRng, AbstractCard.CardRarity.COMMON);
                newCard.upgrade();
                logger.info("New card created: " + newCard.cardID);
                
                ArrayList<AbstractCard> group = null;
                
                if (AbstractDungeon.player.masterDeck.contains(this)){
                    group = AbstractDungeon.player.masterDeck.group;
                    logger.info("Card is in Master Deck.");
                }
                else if (AbstractDungeon.player.hand.contains(this)){
                    group = AbstractDungeon.player.hand.group;
                    logger.info("Card is in Hand.");
                }
                else if (AbstractDungeon.player.limbo.contains(this)){
                    group = AbstractDungeon.player.limbo.group;
                    logger.info("Card is in Limbo");
                }
                else if (AbstractDungeon.player.drawPile.contains(this)){
                    group = AbstractDungeon.player.drawPile.group;
                    logger.info("Card is in Draw Pile");
                }
                else if (AbstractDungeon.player.discardPile.contains(this)){
                    group = AbstractDungeon.player.discardPile.group;
                    logger.info("Card is in Discard Pile");
                }
                else if (AbstractDungeon.player.exhaustPile.contains(this)){
                    group = AbstractDungeon.player.exhaustPile.group;
                    logger.info("Card is in Exhaust Pile");
                }
                else if (AbstractDungeon.gridSelectScreen.selectedCards.contains(this)){
                    group = AbstractDungeon.gridSelectScreen.selectedCards;
                    logger.info("Card is in gridSelectScreen.selectedCards");
                }
                
                // java.lang.NullPointerException
                // at theBrewmaster.cards.Microbrew.upgrade(Microbrew.java:89)
                // at com.megacrit.cardcrawl.screens.select.GridCardSelectScreen.update(GridCardSelectScreen.java:169)
                // at com.megacrit.cardcrawl.dungeons.AbstractDungeon.update(AbstractDungeon.java:2564)
                // at com.megacrit.cardcrawl.core.CardCrawlGame.update(CardCrawlGame.java:876)
                // at com.megacrit.cardcrawl.core.CardCrawlGame.render(CardCrawlGame.java:423)
                // at com.badlogic.gdx.backends.lwjgl.LwjglApplication.mainLoop(LwjglApplication.java:225)
                // at com.badlogic.gdx.backends.lwjgl.LwjglApplication$1.run(LwjglApplication.java:126)
                
                else if (AbstractDungeon.gridSelectScreen.upgradePreviewCard.equals(this)){
                    AbstractDungeon.gridSelectScreen.upgradePreviewCard = newCard.makeStatEquivalentCopy();
                    logger.info("Card is in gridSelectScreen.upgradePreviewCard");
                    return;
                }
                
                if (group == null){
                    logger.info("Card not found elsewhere. Breaking...");
                    return;
                }

                // Upgrade the card
                for (int i = 0; i < group.size(); i++){
                    if (group.get(i).equals(this)){
                        group.set(i, newCard);
                    }
                }
            }

        }
    }

}