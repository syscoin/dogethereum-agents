package org.sysethereum.agents.constants;

import org.bitcoinj.core.Sha256Hash;
import org.libdohj.params.SyscoinMainNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sysethereum.agents.core.bridge.SuperblockData;
import org.sysethereum.agents.core.syscoin.Keccak256Hash;
import org.sysethereum.agents.service.rest.MerkleRootComputer;

import java.util.List;

/**
 * AgentConstants for mainnet
 * Uses Syscoin Mainnet and Eth Mainnet.
 */
public class MainnetAgentConstantsFactory {

    private static final Logger logger = LoggerFactory.getLogger("MainnetAgentConstantsFactory");

    public MainnetAgentConstantsFactory() {
    }

    public AgentConstants create() {
        var syscoinParams = SyscoinMainNetParams.get();

        var syscoinToEthTimerTaskPeriod = 45 * 1000;
        var syscoinToEthTimerTaskPeriodAggressive = 5 * 1000;

        List<Sha256Hash> sysHashes = List.of(Sha256Hash.wrap("33de3e710118e85a26cd3673c9d24c6d044520a6241c37248cf2cacb4a100f0f"));

        var genesisSuperblock = new SuperblockData(
                MerkleRootComputer.computeMerkleRoot(syscoinParams, sysHashes),
                sysHashes,
                1576850904, 1576850690, 402958772,
                Keccak256Hash.wrap(new byte[32]), // initialised with 0s
                1
        );

        var defenderTimerTaskPeriod = 60 * 1000;
        var challengerTimerTaskPeriod = 60 * 1000;
        var defenderConfirmations = 2;
        var challengerConfirmations = 2;

        var networkId = "1"; // eth rinkeby 4; eth mainnet 1

        logger.info("genesisSuperblock " + genesisSuperblock.toString());

        return new AgentConstants(
                syscoinParams,
                syscoinToEthTimerTaskPeriod,
                syscoinToEthTimerTaskPeriodAggressive,
                genesisSuperblock,
                defenderTimerTaskPeriod,
                challengerTimerTaskPeriod,
                defenderConfirmations,
                challengerConfirmations,
                networkId
        );
    }
}
