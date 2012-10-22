/** (C) Copyright 2010 Hal Hildebrand, All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.hellblazer.gossip;

import java.util.List;

/**
 * The communications interface used by the gossip protocol
 * 
 * @author <a href="mailto:hal.hildebrand@gmail.com">Hal Hildebrand</a>
 * 
 */
public interface GossipMessages {
    byte GOSSIP                     = 0;
    byte REPLY                      = 1;
    byte UPDATE                     = 2;
    int  INET_ADDRESS_V6_BYTE_SIZE  = 16;
    int  INET_ADDRESS_MAX_BYTE_SIZE = INET_ADDRESS_V6_BYTE_SIZE // address
                                    + 1 // addressLength
                                    + 4;  // port
    int  NODE_ID_SET_MAX_BYTE_SIZE  = 257;
    int  IDENTITY_BYTE_SIZE         = 16;
    int  HEARTBEAT_STATE_BYTE_SIZE  = IDENTITY_BYTE_SIZE // candidate
                                      + INET_ADDRESS_MAX_BYTE_SIZE // heartbeat address
                                      + NODE_ID_SET_MAX_BYTE_SIZE // msgLinks
                                      + 1 // preferred
                                      + 1 // discoveryOnly
                                      + IDENTITY_BYTE_SIZE // sender
                                      + INET_ADDRESS_MAX_BYTE_SIZE // senderAddress
                                      + 1 // stable
                                      + INET_ADDRESS_MAX_BYTE_SIZE // testInterface
                                      + NODE_ID_SET_MAX_BYTE_SIZE // view
                                      + 4 // viewNumber
                                      + 4; // viewTimeStamp 
    int  DIGEST_BYTE_SIZE           = INET_ADDRESS_MAX_BYTE_SIZE // address
                                    + 8;  // timestamp

    /**
     * Close the communications connection
     */
    void close();

    /**
     * The first message of the gossip protocol. Send a list of the shuffled
     * digests of the receiver's view of the endpoint state
     * 
     * @param digests
     *            - the list of heartbeat digests the receiver knows about
     */
    void gossip(List<Digest> digests);

    /**
     * The second message in the gossip protocol. Send a list of digests the
     * node this handler represents, that would like heartbeat state updates
     * for, along with the list of heartbeat state this node believes is out of
     * date on the node this handler represents.
     * 
     * @param digests
     *            - the digests representing desired state updates
     * @param states
     *            - the updates for the node which are believed to be out of
     *            date
     */
    void reply(List<Digest> digests, List<ReplicatedState> states);

    /**
     * The third message of the gossip protocol. Send a list of updated
     * heartbeat states to the node this handler represents, which is requesting
     * the updates.
     * 
     * @param deltaState
     *            - the list of heartbeat states requested.
     */
    void update(List<ReplicatedState> deltaState);

}