
package com.aleksandar_simeunovic.dto;

import com.aleksandar_simeunovic.enums.MessageType;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class ClientMessage {
    public MessageType type;
    public String message;
}
