package icbm.classic.content.machines;

import com.builtbroken.jlib.data.vector.IPos3D;
import com.builtbroken.mc.api.tile.multiblock.IMultiTile;
import com.builtbroken.mc.api.tile.multiblock.IMultiTileHost;
import com.builtbroken.mc.core.network.IPacketIDReceiver;
import com.builtbroken.mc.core.network.packet.PacketTile;
import com.builtbroken.mc.core.network.packet.PacketType;
import com.builtbroken.mc.lib.transform.vector.Pos;
import com.builtbroken.mc.prefab.tile.Tile;
import icbm.classic.ICBMClassic;
import icbm.classic.Reference;
import icbm.classic.content.explosive.blast.BlastEMP;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;

public class TileEMPTower extends TileICBMMachine implements IMultiTileHost, IPacketIDReceiver
{
    // The maximum possible radius for the EMP to strike
    public static final int MAX_RADIUS = 150;

    public float rotation = 0;
    private float rotationDelta, prevXuanZhuanLu = 0;

    // The EMP mode. 0 = All, 1 = Missiles Only, 2 = Electricity Only
    public byte empMode = 0;

    private int cooldownTicks = 0;

    // The EMP explosion radius
    public int empRadius = 60;

    public TileEMPTower()
    {
        super("empTower", Material.iron);
    }

    @Override
    public Tile newTile()
    {
        return new TileEMPTower();
    }

    @Override
    public void update()
    {
        super.update();

        if (!isReady())
        {
            cooldownTicks--;
        }
        else if (isIndirectlyPowered())
        {
            fire();
        }

        if (ticks % 20 == 0 && getEnergyStored(ForgeDirection.UNKNOWN) > 0)
        {
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, Reference.PREFIX + "machinehum", 0.5F, 0.85F * getEnergyStored(ForgeDirection.UNKNOWN) / getMaxEnergyStored(ForgeDirection.UNKNOWN));
        }

        rotationDelta = (float) (Math.pow(getEnergyStored(ForgeDirection.UNKNOWN) / getMaxEnergyStored(ForgeDirection.UNKNOWN), 2) * 0.5);
        rotation += rotationDelta;
        if (rotation > 360)
        {
            rotation = 0;
        }

        prevXuanZhuanLu = rotationDelta;
    }

    @Override
    public boolean read(ByteBuf data, int id, EntityPlayer player, PacketType type)
    {
        if (!super.read(data, id, player, type))
        {
            switch (id)
            {
                case 0:
                {
                    energy = data.readInt();
                    empRadius = data.readInt();
                    empMode = data.readByte();
                    return true;
                }
                case 1:
                {
                    empRadius = data.readInt();
                    return true;
                }
                case 2:
                {
                    empMode = data.readByte();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return Math.max(300000000 * (this.empRadius / MAX_RADIUS), 1000000000);
    }

    @Override
    public PacketTile getDescPacket()
    {
        return new PacketTile(this, 0, getEnergyStored(ForgeDirection.UNKNOWN), this.empRadius, this.empMode);
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        this.empRadius = par1NBTTagCompound.getInteger("banJing");
        this.empMode = par1NBTTagCompound.getByte("muoShi");
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setInteger("banJing", this.empRadius);
        par1NBTTagCompound.setByte("muoShi", this.empMode);
    }

    //@Callback(limit = 1)
    public boolean fire()
    {
        if (this.checkExtract())
        {
            if (isReady())
            {
                switch (this.empMode)
                {
                    default:
                        new BlastEMP(this.worldObj, null, this.xCoord, this.yCoord, this.zCoord, this.empRadius).setEffectBlocks().setEffectEntities().explode();
                        break;
                    case 1:
                        new BlastEMP(this.worldObj, null, this.xCoord, this.yCoord, this.zCoord, this.empRadius).setEffectEntities().explode();
                        break;
                    case 2:
                        new BlastEMP(this.worldObj, null, this.xCoord, this.yCoord, this.zCoord, this.empRadius).setEffectBlocks().explode();
                        break;
                }
                this.extractEnergy();
                this.cooldownTicks = getMaxCooldown();
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onPlayerRightClick(EntityPlayer player, int side, Pos hit)
    {
        if (isServer())
        {
            openGui(player, ICBMClassic.INSTANCE);
        }
        return true;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

    //==========================================
    //==== Open Computers code
    //=========================================
    //@Override
    public String getComponentName()
    {
        return "emptower";
    }

    //@Callback
    public byte getEmpMode()
    {
        return empMode;
    }

    //@Callback
    public void setEmpMode(byte empMode)
    {
        if (empMode >= 0 && empMode <= 2)
        {
            this.empMode = empMode;
        }
    }

    //@Callback
    public void empMissiles()
    {
        this.empMode = 1;
    }

    //@Callback
    public void empAll()
    {
        this.empMode = 0;
    }

    //@Callback
    public void empElectronics()
    {
        this.empMode = 2;
    }

    //@Callback
    public int getEmpRadius()
    {
        return empRadius;
    }

    //@Callback
    public int getMaxEmpRadius()
    {
        return MAX_RADIUS;
    }

    //@Callback
    public void setEmpRadius(int empRadius)
    {
        int prev = getEmpRadius();
        this.empRadius = Math.min(Math.max(empRadius, 0), MAX_RADIUS);
    }

    //@Callback
    public static int getMaxRadius()
    {
        return MAX_RADIUS;
    }

    //@Callback
    public boolean isReady()
    {
        return getCooldown() <= 0;
    }

    //@Callback
    public int getCooldown()
    {
        return cooldownTicks;
    }

    //@Callback
    public int getMaxCooldown()
    {
        return 120;
    }

    //==========================================
    //==== Multi-Block code
    //=========================================

    @Override
    public void onMultiTileAdded(IMultiTile tileMulti)
    {

    }

    @Override
    public boolean onMultiTileBroken(IMultiTile tileMulti, Object source, boolean harvest)
    {
        return false;
    }

    @Override
    public void onTileInvalidate(IMultiTile tileMulti)
    {

    }

    @Override
    public boolean onMultiTileActivated(IMultiTile tile, EntityPlayer player, int side, IPos3D hit)
    {
        return false;
    }

    @Override
    public void onMultiTileClicked(IMultiTile tile, EntityPlayer player)
    {

    }

    @Override
    public HashMap<IPos3D, String> getLayoutOfMultiBlock()
    {
        HashMap<IPos3D, String> map = new HashMap();
        map.put(new Pos(0, 1, 0), "");
        map.put(new Pos(0, 2, 0), "");
        return map;
    }

    public boolean hasPower()
    {
        return true;
    }
}