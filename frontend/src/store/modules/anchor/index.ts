import { defineStore } from 'pinia'

const useAnchorStore = defineStore('anchor', {
  state: (): AnchorState[] => <AnchorState[]>[],
  actions: {
    updateAnchorState(partial: AnchorState[]) {
      this.$state = partial
    },
    clear() {
      this.$state.length = 0
    }
  }
})

export default useAnchorStore
