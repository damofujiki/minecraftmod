package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.skill.effect.AbstractInvoker;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.HelperChestUnsaga;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsagaNew;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Optional;
import com.hinasch.lib.HSLibs;
import com.hinasch.lib.ScanHelper;

public class InvokeSpell extends AbstractInvoker{

	//public EntityLivingBase invoker;
	protected ItemStack magicItem;
	public int prob;
	protected Spell spell;
	protected ItemStack spellbook;
	protected SpellBase spellEffect;
	protected EntityLivingBase target;
	protected UnsagaElement worldElement;
	
	public InvokeSpell(Spell spell,World world,EntityLivingBase ep,ItemStack is){
		super(world,ep);
		this.spell = spell;
		this.spellbook = is;
		this.worldElement = UnsagaMagic.worldElement;
		this.spellEffect = this.spell.getSpellEffect();
		this.spellEffect.setWorldHelper(world);
		//this.isSneak = false;
	}
	
	public InvokeSpell(Spell spell,World world,EntityPlayer ep,ItemStack is,EntityLivingBase target){
		this(spell,world,ep,is);
		this.target = target;
		
	}


	public EntityLivingBase getInvoker(){
		return this.owner;
	}
	
	public void damageSpellBook(){
		this.spellbook.damageItem((int)((float)this.spell.damegeItem * ItemSpellBook.getCost(spellbook)), getInvoker());
	}
	
	public int figureSuccessProb(){
		worldElement.figureElements(world, getInvoker());
		SpellMixTable currentElement = worldElement.getWorldElements();
		float probfloat = (float)this.spell.baseProbability + (float)HSLibs.exceptZero(currentElement.getInt(this.spell.element))*0.6F;
		int prob = Math.round(probfloat);
		MathHelper.clamp_int(prob, 15, 100);
		this.prob = prob;
		return prob;
	}
	
	public float getAmp(){
		float amp = ItemSpellBook.getAmp(this.spellbook);
		if(LivingDebuff.hasDebuff(getInvoker(), Debuffs.downMagic)){
			amp = amp /2;
			amp = MathHelper.clamp_float(amp, 0.1F, 10.0F);
			
		}
		return amp;
	}
	
	public int getCost(){
		//todo:まわりの五行地も反映するように
		int spellcost = this.spell.damegeItem;
		return Math.round(((float)spellcost*ItemSpellBook.getCost(this.spellbook)));
	}
	
	public DamageSourceUnsaga getDamageSource(){
		DamageSourceUnsaga ds = new DamageSourceUnsaga(null,this.getInvoker(),this.getSpell().hurtLP,DamageHelper.Type.MAGIC);
		ds.setMagicDamage();
		ds.setDamageBypassesArmor();
		return ds;
	}
	
	public Optional<ItemStack> getMagicItem(){
		if(this.magicItem!=null){
			return Optional.of(this.magicItem);
		}
		return Optional.absent();
	}
	
	public Spell getSpell(){
		return this.spell;
	}
	public Optional<EntityLivingBase> getTarget(){
		if(target!=null){
			return Optional.of(this.target);
		}
		return Optional.absent();
	}
	
	public void run(){
		if(!this.world.isRemote && this.tryInvoke()){
			this.tryUnlockMagicLock();
			ChatUtil.addMessageNoLocalized(getInvoker(), Translation.localize("msg.spell.succeeded")+"("+prob+"%)");
			PacketParticle pp = new PacketParticle(this.getSpell().element.getElementParticle(),this.getInvoker().getEntityId(),6);
			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(this.getInvoker()));
			this.spellEffect.invoke(this);
			if(this.getMagicItem().isPresent()){
				this.getMagicItem().get().damageItem(this.getCost(), this.getInvoker());
			}
		}else{
			ChatUtil.addMessageNoLocalized(getInvoker(), Translation.localize("msg.spell.failed")+"("+prob+"%)");

		}
	}
	
	public void setMagicItem(ItemStack is){
		this.magicItem = is;
	}
	
	public boolean tryInvoke(){
		return this.world.rand.nextInt(100)<this.figureSuccessProb() ? true : false;
	}
	

	public void tryUnlockMagicLock(){
		if(this.getInvoker() instanceof EntityPlayer){
			ScanHelper scan = new ScanHelper((EntityPlayer) this.getInvoker(),3,3);
			for(;scan.hasNext();scan.next()){
				TileEntity te = this.world.getTileEntity(scan.sx, scan.sy, scan.sz);
				if(te instanceof TileEntityChestUnsagaNew){
					TileEntityChestUnsagaNew chest = (TileEntityChestUnsagaNew)te;
					HelperChestUnsaga hc = new HelperChestUnsaga(chest);
					if(hc.tryUnlockMagicalLock()){
						ChatUtil.addMessage(getInvoker(), "msg.chest.magiclock.unlocked");
						
					}
				}
			}
		}

	}
	
	
	public EntityLivingBase getTargetOrfindTarget(){
		if(this.getTarget().isPresent()){
			return this.getTarget().get();
		}
		return LockOnHelper.searchEntityNear(getInvoker(), Debuffs.spellTarget);
		
	}
}
