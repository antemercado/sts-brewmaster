package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.PartyFavorsAction;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PartyFavors extends AbstractBrewmasterCard {
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = -1;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(PartyFavors.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(PartyFavors.class.getSimpleName(), TYPE);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public PartyFavors() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        CardGroup microbrews = BrewmasterMod.getMicrobrews();
        for (AbstractCard c: microbrews.group){
            c.upgrade();
        }
        AbstractCard[] previews = new AbstractCard[microbrews.size()];
        previews = microbrews.group.toArray(previews);
        MultiCardPreview.add(this, previews);
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = this.energyOnUse;
        if (upgraded){
            amount += 1;
        }
        addToBot(new PartyFavorsAction(amount, this.freeToPlayOnce));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}