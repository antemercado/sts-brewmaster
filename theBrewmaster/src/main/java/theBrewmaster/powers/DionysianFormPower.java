package theBrewmaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.util.TextureLoader;

import static theBrewmaster.BrewmasterMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DionysianFormPower extends AbstractPower{
    public AbstractCreature source;

    private CardGroup copyGroup;

    public static final String POWER_ID = BrewmasterMod.makeID("DionysianFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public DionysianFormPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        this.copyGroup = new CardGroup(CardGroupType.UNSPECIFIED);
        
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.copyGroup.clear();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.hasTag(CustomTags.BREW) || card.purgeOnUse){
            return;
        }

        flash();
        AbstractMonster m = null;
        if (action.target != null){
            m = (AbstractMonster)action.target;
        } else {
            m = AbstractDungeon.getRandomMonster();
        }
        for (AbstractCard c : this.copyGroup.group){
            AbstractCard tmp = c.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;

            if (m != null){
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            for (int i = 0; i < this.amount; i++){
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m ,card.energyOnUse, true, true), true);
            }
        }

        this.copyGroup.addToBottom(card.makeCopy());

    }

    // Update the description
    @Override
    public void updateDescription() {
        if (this.amount > 1){
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

}