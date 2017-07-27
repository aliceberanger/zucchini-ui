package io.zucchiniui.backend;

import dagger.Subcomponent;
import io.zucchiniui.backend.presence.websocket.PresenceEndpoint;
import io.zucchiniui.backend.support.dagger.RequestScope;

@Subcomponent
@RequestScope
public interface RequestScopeComponent {

    PresenceEndpoint presenceEndpoint();

}
