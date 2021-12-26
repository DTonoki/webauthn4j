package com.webauthn4j.anchor;

import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.webauthn4j.util.CertificateUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.InputStream;
import java.security.cert.TrustAnchor;
import java.util.*;

/**
 * NOT FOR PRODUCTION USE
 */
@Deprecated
public class SampleTrustAnchorsProvider extends CachingTrustAnchorsProviderBase {

    private final List<String> classPaths;

    public SampleTrustAnchorsProvider() {
        this.classPaths = Collections.singletonList("attestation/google/google-root-CA.crt");
    }

    @Override
    protected @NonNull Map<AAGUID, Set<TrustAnchor>> loadTrustAnchors() {
        Set<TrustAnchor> set = new HashSet<>();
        for (String classPath : classPaths) {
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream(classPath);
            TrustAnchor trustAnchor = new TrustAnchor(CertificateUtil.generateX509Certificate(inputStream), null);
            set.add(trustAnchor);
        }
        return Collections.singletonMap(null, set);
    }

}
