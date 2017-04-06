package Epidermis_Model;
import AgentFramework.GenomeInfo;
import AgentFramework.Utils;
import cern.jet.random.Poisson;

import java.util.ArrayList;
import java.util.Random;

//import static Epidermis_Model.EpidermisBases.BaseIndex;
import static Epidermis_Model.EpidermisCell.RNEngine;

/**
 * Created by schencro on 3/25/17.
 */

public class EpidermisCellGenome extends GenomeInfo <EpidermisCellGenome> {
    /*
    New Information To Keep Inside the Model!!!!! Official Information
     */
    static final int GenomeComponents = 71;
    static final double HumanGenome = 3200000000.0;
    static final String[] GeneNames = new String[]{"Genome","ADAM29","ADAMTS18","AJUBA","AKT1","AKT2","APOB","ARID1A","ARID2","AURKA","BAI3","BRAF","CASP8","CCND1","CDH1","CDKN2A","CR2","CREBBP","CUL3","DICER1","EGFR","EPHA2","ERBB2","ERBB3","ERBB4","EZH2","FAT1","FAT4","FBXW7","FGFR1","FGFR2","FGFR3","FLG2","GRIN2A","GRM3","HRAS","IRF6","KCNH5","KEAP1","KRAS","MET","MUC17","NF1","NFE2L2","NOTCH1","NOTCH2","NOTCH3","NRAS","NSD1","PCED1B","PIK3CA","PLCB1","PPP1R3A","PREX2","PTCH1","PTEN","PTPRT","RB1","RBM10","SALL1","SCN11A","SCN1A","SETD2","SMAD4","SMO","SOX2","SPHKAP","SUFU","TP53","TP63","TRIOBP"};
    static final long[] GeneLengths = new long[]{3191618082l,2463l,3666l,1617l,1443l,1444l,13692l,6205l,5506l,1212l,4569l,2301l,1440l,888l,2649l,514l,3279l,7329l,2307l,5767l,3633l,2931l,3695l,4023l,3927l,2239l,13767l,14944l,2124l,2616l,2614l,2443l,7176l,4391l,2781l,633l,1404l,1976l,1875l,687l,4185l,13482l,8520l,1771l,7668l,7416l,6966l,570l,7351l,1299l,3207l,3750l,3369l,4821l,4337l,1212l,4383l,2787l,2951l,3985l,5376l,6026l,7695l,1659l,2364l,954l,5103l,1455l,1289l,2041l,7098};
    static final double[] ExpectedMuts = new double[]{1.02E+01,1.2298960944e-05,1.96790865336e-05,5.17789144098534e-06,4.0831751376e-06,3.4550775719999997e-06,5.31298384596e-05,1.09091587995e-05,1.4262587681399999e-05,5.799936312e-06,3.26067900354e-05,7.5642604596e-06,3.1009560480000003e-06,2.632816548e-06,4.9328483301e-06,1.4758253784000002e-06,1.01311941753e-05,1.31902673427e-05,7.5599847855e-06,1.7638435243800002e-05,1.5155579382299998e-05,8.232701246999999e-06,8.4182405355e-06,1.0923286209299999e-05,2.01405160341e-05,7.383735465300001e-06,5.52827183922e-05,7.44672207456e-05,4.5076216176e-06,6.3409807368e-06,6.0913895615999996e-06,6.023301272100001e-06,2.7098910352799996e-05,1.43892038115e-05,1.96441633269e-05,1.9557932031000003e-06,4.7323285776e-06,8.1609406632e-06,4.252267125e-06,2.5443069732000003e-06,1.1842056792e-05,4.2777651231e-05,1.9588205052e-05,4.5322769646000005e-06,2.17774750284e-05,2.8159527204e-05,2.39869062126e-05,1.577318022e-06,2.11065999156e-05,4.15960481251698e-06,1.3912263288900002e-05,1.5799708125e-05,1.98371928474e-05,3.12956873424e-05,9.8357773446e-06,2.3451916392e-06,2.41207004817e-05,7.1323861662e-06,7.7055865731e-06,1.6665454107e-05,1.33106416128e-05,2.2693447177199998e-05,1.28883485745e-05,5.3329515560999995e-06,5.4104442479999995e-06,4.0392948618e-06,2.79768591711e-05,2.5580702745e-06,2.9903398857e-06,6.2211890403e-06,1.60235412246e-05};
    static final double[] BaseMutProb = new double[]{1.0/6,3/6.,1.0/6,1.0/6};
//    static final long[][][] BaseIndex = EpidermisBases.BaseIndex;
    static final Random RN=new Random();
    String PrivateGenome;
    float r;
    float b;
    float g;
    /*
    End New Information To Keep Inside the Model!!!!!
     */

    EpidermisCellGenome(float r, float b, float g, String PrivateGenome) {
        this.r = r;
        this.b = b;
        this.g = g;
        this.PrivateGenome = PrivateGenome;
    }

//    static final int genomelength = 73;
//    static final double humangenome = 3200000000.0;
//    static final long[] genelengths=new long[]{3191618082l, 59823,108204,8731,24252,50898,42645,45841,71006,22907,753772,190752,20074,13370,98250,7382,35596,155066,115248,24908,137920,26125,28662,8630,1162911,76558,20134,98997,123939,56708,115638,15561,11270,419393,220963,3309,20553,338011,16686,45675,59146,41142,38777,112449,33587,31229,127078,32403,8901,12431,145748,20439,86187,519254,42201,158722,49677,105338,1117166,59211,41598,14392,15015,84511,88892,54829,16034,2512,201692,114698,13018,107620,29653};
//    static final double[] geneproportion=new double[]{0.997380650625, 1.86946875e-05, 3.381375e-05, 2.7284375e-06, 7.57875e-06, 1.5905625e-05, 1.33265625e-05, 1.43253125e-05, 2.2189375e-05, 7.1584375e-06, 0.00023555375, 5.961e-05, 6.273125e-06, 4.178125e-06, 3.0703125e-05, 2.306875e-06, 1.112375e-05, 4.8458125e-05, 3.6015e-05, 7.78375e-06, 4.31e-05, 8.1640625e-06, 8.956875e-06, 2.696875e-06, 0.0003634096875, 2.3924375e-05, 6.291875e-06, 3.09365625e-05, 3.87309375e-05, 1.772125e-05, 3.6136875e-05, 4.8628125e-06, 3.521875e-06, 0.0001310603125, 6.90509375e-05, 1.0340625e-06, 6.4228125e-06, 0.0001056284375, 5.214375e-06, 1.42734375e-05, 1.8483125e-05, 1.2856875e-05, 1.21178125e-05, 3.51403125e-05, 1.04959375e-05, 9.7590625e-06, 3.9711875e-05, 1.01259375e-05, 2.7815625e-06, 3.8846875e-06, 4.554625e-05, 6.3871875e-06, 2.69334375e-05, 0.000162266875, 1.31878125e-05, 4.9600625e-05, 1.55240625e-05, 3.2918125e-05, 0.000349114375, 1.85034375e-05, 1.2999375e-05, 4.4975e-06, 4.6921875e-06, 2.64096875e-05, 2.777875e-05, 1.71340625e-05, 5.010625e-06, 7.85e-07, 6.302875e-05, 3.5843125e-05, 4.068125e-06, 3.363125e-05, 9.2665625e-06};
//    static final long[][][] BaseIndex = new long[][][]{{{5}};
//    static final double[] GeneMutations=new double[]{10.22006923295100000000,0.00001229896094400000,0.00001967908653360000,0.00000517789144099000,0.00000408317513760000,0.00000345986299800000,0.00005312983845960000,0.00001091267504730000,0.00001426776842520000,0.00000579993631200000,0.00001088320306500000,0.00000756426045960000,0.00000310095604800000,0.00000263281654800000,0.00000493284833010000,0.00000114563098440000,0.00001013119417530000,0.00001319026734270000,0.00000755998478550000,0.00001764455226660000,0.00001515557938230000,0.00000823270124700000,0.00000837950979420000,0.00001093957746390000,0.00000452866708890000,0.00000739033103070000,0.00005528271839220000,0.00007447718691540000,0.00000450762161760000,0.00000591194648970000,0.00000575349687360000,0.00000596905950870000,0.00002709891035280000,0.00001440231171750000,0.00001864818093600000,0.00000158502671910000,0.00000473232857760000,0.00000774380756250000,0.00000425226712500000,0.00000211099705200000,0.00001180810107360000,0.00004277765123100000,0.00001958820505200000,0.00000452971780200000,0.00002177747502840000,0.00002815952720400000,0.00002398690621260000,0.00001537800806340000,0.00000157731802200000,0.00002091422579040000,0.00000415960481252000,0.00001391226328890000,0.00001483908587100000,0.00001983719284740000,0.00003129568734240000,0.00000984484884780000,0.00000234519163920000,0.00002412070048170000,0.00000713238616620000,0.00000729302043330000,0.00001508045859720000,0.00001331064161280000,0.00002270851086600000,0.00001288834857450000,0.00000533295155610000,0.00000541044424800000,0.00000403929486180000,0.00002797685917110000,0.00000255807027450000,0.00000238020847380000,0.00000622728525690000,0.00001602354122460000};

    @Override
    public EpidermisCellGenome _RunPossibleMutation() {
        StringBuilder MutsObtained = new StringBuilder();
        for(int j=0; j<ExpectedMuts.length; j++){
            if (j!=0) {
                Poisson poisson_dist = new Poisson(ExpectedMuts[j], RNEngine); // Setup the Poisson distributions for each gene.
                int mutations = poisson_dist.nextInt(); // Gets how many mutations will occur for each gene
                for(int hits=0; hits<mutations; hits++){
                    int MutatedBaseKind = Utils.RandomVariable(BaseMutProb, RN);
                    //long mutIndex = BaseIndex[j][MutatedBaseKind][RN.nextInt(BaseIndex[j][MutatedBaseKind].length)];
                    long mutIndex = 111111111111l;
                    String MutOut = j + "." + mutIndex + ",";
                    MutsObtained.append(MutOut);
                }
            }
        }
        String PrivGenome = MutsObtained.toString();
        if(PrivGenome.length()>0){
            return new EpidermisCellGenome(RN.nextFloat() * 0.9f + 0.1f, RN.nextFloat() * 0.9f + 0.1f, RN.nextFloat() * 0.9f + 0.1f, PrivGenome);
        } else{
            return null; // If No Mutation Occurs
        }
    }

    @Override
    public String GenomeInfoStr() {
        return PrivateGenome;
    }
}
