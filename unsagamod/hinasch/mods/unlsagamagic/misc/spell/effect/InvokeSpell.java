package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.ScanHelper;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellBlend;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Optional;

public class InvokeSpell {

	public int prob;
	public EntityPlayer invoker;
	public World world;
	protected Spell spell;
	protected ItemStack spellbook;
	protected UnsagaElement worldElement;
	protected SpellEffect spellEffect;
	protected EntityLivingBase target;
	protected ItemStack magicItem;
	
	public InvokeSpell(Spell spell,World world,EntityPlayer ep,ItemStack is){
		this.spell = spell;
		this.invoker = ep;
		this.world = world;
		this.spellbook = is;
		this.worldElement = UnsagaMagic.worldElement;
		if(spell instanceof SpellBlend){
			this.spellEffect = SpellRegistry.spellEffectBlend;
		}else{
			this.spellEffect = SpellRegistry.spellEffectNormal;
		}
		
	}
	
	public void tryUnlockMagicLock(){
		ScanHelper scan = new ScanHelper(this.invoker,3,3);
		for(;scan.hasNext();scan.next()){
			TileEntity te = this.world.getBlockTileEntity(scan.sx, scan.sy, scan.sz);
			if(te instanceof TileEntityChestUnsaga){
				TileEntityChestUnsaga chest = (TileEntityChestUnsaga)te;
				if(chest.tryUnlockMagicalLock()){
					this.invoker.addChatMessage(Translation.localize("msg.chest.magiclock.unlocked"));
				}
			}
		}
	}
	
	public void setMagicItem(ItemStack is){
		this.magicItem = is;
	}
	
	public Optional<ItemStack> getMagicItem(){
		if(this.magicItem!=null){
			return Optional.of(this.magicItem);
		}
		return Optional.absent();
	}
	public InvokeSpell(Spell spell,World world,EntityPlayer ep,ItemStack is,EntityLivingBase target){
		this(spell,world,ep,is);
		this.target = target;
		
	}
	
	public float getAmp(){
		float amp = ItemSpellBook.getAmp(this.spellbook);
		if(LivingDebuff.hasDebuff(invoker, DebuffRegistry.downMagic)){
			amp = amp /2;
			amp = MathHelper.clamp_float(amp, 0.1F, 10.0F);
			
		}
		return amp;
	}
	
	public Optional<EntityLivingBase> getTarget(){
		if(target!=null){
			return Optional.of(this.target);
		}
		return Optional.absent();
	}
	
	public int getCost(){
		int spellcost = this.spell.damegeItem;
		return Math.round(((float)spellcost*ItemSpellBook.getCost(this.spellbook)));
	}
	public void run(){
		if(!this.world.isRemote && this.tryInvoke()){
			this.tryUnlockMagicLock();
			this.invoker.addChatMessage(Translation.localize("msg.spell.succeeded")+"("+prob+"%)");
			this.spellEffect.doEffect(this);
			if(this.getMagicItem().isPresent()){
				this.getMagicItem().get().damageItem(this.getCost(), this.invoker);
			}
		}else{
			this.invoker.addChatMessage(Translation.localize("msg.spell.failed")+"("+prob+"%)");
		}
	}
	
	public Spell getSpell(){
		return this.spell;
	}
	
	public void damageSpellBook(){
		this.spellbook.damageItem((int)((float)this.spell.damegeItem * ItemSpellBook.getCost(spellbook)), invoker);
	}
	
	public int figureSuccessProb(){
		worldElement.figureElements(world, invoker);
		SpellMixTable currentElement = worldElement.getWorldElements();
		float probfloat = (float)this.spell.baseProbability + (float)HSLibs.exceptZero(currentElement.getInt(this.spell.element))*0.6F;
		int prob = Math.round(probfloat);
		MathHelper.clamp_int(prob, 15, 100);
		this.prob = prob;
		return prob;
	}
	
	public boolean tryInvoke(){
		return this.world.rand.nextInt(100)<this.figureSuccessProb() ? true : false;
	}
	
	
}
