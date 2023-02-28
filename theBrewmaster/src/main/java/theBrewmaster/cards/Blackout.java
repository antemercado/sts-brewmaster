package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.ApplyIntoxicationPower;
import theBrewmaster.actions.BlackoutAction;
import theBrewmaster.cards.status.WoozyStatus;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Blackout extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    
    private static final int MAGIC2 = 100;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(Blackout.class.getSimpleName());
    public static final String IMG = makeCardPath(Blackout.class.getSimpleName(), TYPE);
    
    public Blackout() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseMagicNumber2 = this.magicNumber2 = MAGIC2;
        
        this.cardsToPreview = new WoozyStatus();
        this.exhaust = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, this.magicNumber2)));
        // addToBot(new MakeTempCardInDiscardAction(new WoozyStatus(), this.magicNumber));
        addToBot(new BlackoutAction(this.magicNumber, this.magicNumber2));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}