package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.cards.status.WoozyStatus;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BeerBelly extends AbstractBrewmasterCard {
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = 1;

    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLOCK = 5;

    private static final int MAGIC = 2;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BeerBelly.class.getSimpleName());
    public static final String IMG = makeCardPath(BeerBelly.class.getSimpleName(), TYPE);

    public BeerBelly() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = MAGIC;

        this.cardsToPreview = new WoozyStatus();
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(new WoozyStatus(), this.magicNumber, true, true));
        addToBot(new GainBlockAction(p, p, block));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}