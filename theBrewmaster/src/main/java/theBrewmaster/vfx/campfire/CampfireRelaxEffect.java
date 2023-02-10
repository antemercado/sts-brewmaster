package theBrewmaster.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import theBrewmaster.relics.CozyBeerRelic;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

public class CampfireRelaxEffect extends AbstractGameEffect{
    private static final float DUR = 2.0f;
    private boolean hasRelaxed = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    

    public CampfireRelaxEffect() {
        this.duration = DUR;
        this.screenColor.a = 0.0f;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();

        if (this.duration < 1.0f && !this.hasRelaxed){
            this.hasRelaxed = true;
            
            if (AbstractDungeon.player.hasRelic(CozyBeerRelic.ID)){
                AbstractDungeon.player.getRelic(CozyBeerRelic.ID).flash();
                AbstractDungeon.player.getRelic(CozyBeerRelic.ID).counter += 50;
                CardCrawlGame.sound.play("POTION_1");
                CardCrawlGame.metricData.addCampfireChoiceData("RELAX");
            }
        }
        
        if (this.duration < 0.0f){
            this.isDone = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        } 
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }
    
    public void dispose() {
    }
}

