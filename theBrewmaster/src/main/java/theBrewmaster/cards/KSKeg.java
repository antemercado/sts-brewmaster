package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;

import theBrewmaster.DefaultMod;
import theBrewmaster.characters.TheBrewmaster;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.tags.CustomTags;
import static theBrewmaster.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSKeg extends AbstractDynamicCard {
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID(KSKegP.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBrewmaster.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int POWER = 15;

    private AbstractCard preview;

    public KSKeg() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = POWER;

        this.exhaust = true;

        this.preview = new KSSmashP();
        this.cardsToPreview = this.preview;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, magicNumber)));

            AbstractCard smash = new KSSmash();
            if (upgraded)
                smash.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(smash, 1, false));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.preview.upgrade();
        }
    }
}