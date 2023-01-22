package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.DefaultMod;
import theBrewmaster.characters.TheBrewmaster;
import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.stances.IntoxicatedStance;

import static theBrewmaster.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickSip extends AbstractDynamicCard {
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID(QuickSip.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBrewmaster.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int POWER = 10;
    private static final int ENERGY_GAIN = 1;
    private static final int CARD_DRAW = 1;

    public QuickSip() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = POWER;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, magicNumber)));
        if (AbstractDungeon.player.stance.ID.equals(IntoxicatedStance.STANCE_ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
            if (upgraded){
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, CARD_DRAW));
            }
        }
    }

    // Glow when Intoxicated
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.stance.ID.equals(IntoxicatedStance.STANCE_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
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