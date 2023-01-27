package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.stances.IntoxicatedStance;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

public class FairyBrew extends AbstractDynamicCard {
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(FairyBrew.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int MAGIC = 3;
    private static final int MAGIC2 = 6;

    public FairyBrew() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseMagicNumber2 = this.magicNumber2 = MAGIC2;

        this.exhaust = true;
        
        tags.add(CustomTags.BREW);
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean intoxicated = AbstractDungeon.player.stance.ID.equals(IntoxicatedStance.STANCE_ID);

        if (!intoxicated)
            addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
        if (intoxicated)
            addToBot(new HealAction(p, p, magicNumber2));
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
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}