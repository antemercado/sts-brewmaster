package theBrewmaster.powers;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomTags;

import static theBrewmaster.BrewmasterMod.makePowerPath;
import theBrewmaster.util.TextureLoader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;




public class DoubleFistPower extends AbstractPower{
    public AbstractCreature source;
    
    public static final String POWER_ID = BrewmasterMod.makeID("DoubleFistPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    
    public DoubleFistPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if (card.hasTag(CustomTags.BREW) && !card.purgeOnUse && this.amount > 0){
            flash();
            AbstractMonster m = null;

            if (action.target != null){
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;

            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m ,card.energyOnUse, true, true), true);
            
            this.amount--;
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DoubleFistPower.POWER_ID));
            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DoubleFistPower.POWER_ID));
        }
    }

    @Override
    public void updateDescription(){
        if (amount == 1){
            description = DESCRIPTIONS[0];
        }
        if (amount > 1){
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

}
