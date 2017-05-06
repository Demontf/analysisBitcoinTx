# analysisBitcoinTx

 This project is based on project [priiduneemre](https://github.com/priiduneemre/btcd-cli4j) and project [btcsuite](https://github.com/btcsuite/btcd)


btcd is an alternative full node bitcoin implementation written in Go (golang).

This project is currently under active development and is in a Beta state. It is extremely stable and has been in production use since October 2013.

I start up a full node bitcoin client on my ubuntu server and startup RPC server for experiment. The details of the server is:
 
 * version: Ubuntu 14.04.5 LTS
 * ip:10.108.20.13(for lab mate)
 * rpc information is [here](https://github.com/Demontf/analysisBitcoinTx/blob/master/src/node_config.properties)

The btcd-cli4j library is a simple Java wrapper around Bitcoin Core's JSON-RPC (via HTTP) interface

I use the `Latest release 0.5.1` jar to get information about Bitcoin and save as the special data structure in mysql for the transaction analysis. 