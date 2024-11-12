package com.pharma.rest.model;

import lombok.extern.jackson.Jacksonized;

@Jacksonized
public record QRCode(String base64QRCode) {
}
